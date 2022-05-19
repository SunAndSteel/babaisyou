package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Material;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe qui permet de gérer les règles du jeu
 */
public class Rule {

    private final GameObject obj1, obj2, is;
    private final Material material1, material2;
    private final Effect effect;

    private Rule(GameObject obj1, GameObject is, GameObject obj2, Level level) {

        this.obj1 = obj1;
        this.obj2 = obj2;
        this.is = is;

        // Permet de rendre la règle lumineuse.
        Glow glowEffect = GameObject.getGlowEffect();
        obj1.getIv().setEffect(glowEffect);
        obj2.getIv().setEffect(glowEffect);
        is.getIv().setEffect(glowEffect);


        Material materialObj1 = obj1.getMaterial();
        Material materialObj2 = obj2.getMaterial();

        material1 = Material.valueOf(materialObj1.getNameObject());

        if (materialObj2.hasNameObject()) {
            material2 = Material.valueOf(materialObj2.getNameObject());
            effect = null;
        } else {
            material2 = null;
            effect = materialObj2.getEffect();
        }

        obj1.getAssociatedRules().add(this);
        obj2.getAssociatedRules().add(this);
        is.getAssociatedRules().add(this);
        level.getRules().add(this);

        // On fait une copie des instances, car la liste est modifiée dans setMaterial,
        // et donc pour ne pas avoir de problème avec le for each.
        ArrayList<GameObject> instances = new ArrayList<>(level.getInstances().get(material1));

        if (materialObj2.hasEffect()) {

            for (GameObject object : instances) {
                object.addTag(materialObj2.getEffect(), level);
            }

        } else {

            Material newMaterial = Material.valueOf(materialObj2.getNameObject());

            for (GameObject object : instances) {
                object.setMaterial(newMaterial, level);
            }
        }
    }

    /**
     * Permet de construire une règle.
     * @param obj1 Le premier objet de la règle.
     * @param is L'objet Is de la règle.
     * @param obj2 Le deuxième objet de la règle.
     * @param level Le niveau.
     */
    public static void createRule(GameObject obj1, GameObject is, GameObject obj2, Level level) {

        Material mat1 = obj1.getMaterial();
        Material mat2 = obj2.getMaterial();

        // Vérifie que l'obj1 est bien un text et que l'obj2 est bien un text ou une règle.
        if (!mat1.hasNameObject() || (!mat2.hasNameObject() && !mat2.hasEffect())) {
            return;
        }

        // Vérifie que la règle n'existe pas déjà
        for (Rule rule : level.getRules()) {
            if (rule.obj1 == obj1 && rule.obj2 == obj2) {
                return;
            }
        }

        new Rule(obj1, is, obj2, level);

    }

    /**
     * Permet de chercher toutes les règles.
     * @param level Le niveau.
     */
    public static void checkAllRules(Level level) {

        for (GameObject is : level.getInstances().get(Material.Is)) {
            ruleIs(is, level);
        }
    }

    /**
     * Recherche les règles du niveau par rapport aux objets qui on bouge en derniers.
     * @param level Le niveau
     */
    public static void checkRules(Level level) {
        Map<GameObject, Direction> movedObjects = Mouvement.getMovedObjects();

        ArrayList<GameObject> copyMovedObjectKeySet = new ArrayList<>(movedObjects.keySet());

        for (GameObject object : copyMovedObjectKeySet) {

            ArrayList<Rule> associatedRules = new ArrayList<>(object.getAssociatedRules());

            // On vérifie si les anciennes règles sont toujours valide.
            for (Rule rule : associatedRules) {
                if (!rule.stillValid(level)) {
                    rule.unRule(level);
                }
            }

            int x = object.getX();
            int y = object.getY();

            // On vérifie si l'objet est bien toujours là (c'est seulement utile lorsque que l'objet s'est fait supprimer).
            if (!level.get(x, y).contains(object))
                continue;

            Material material = object.getMaterial();
            Direction[] directions;

            if (material == Material.Is) {
                ruleIs(object, level);
                continue;

            // Si l'objet a un nameObject, donc est un objet text, l'objet peut former une règle dans les equates direction.
            } else if (material.hasNameObject()) {
                directions = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

            // Si l'objet a un effet, donc est un objet règle, l'objet peut former une règle que dans deux directions.
            } else if (material.hasEffect()) {
                directions = new Direction[]{Direction.UP, Direction.LEFT};

            } else {
                continue;

            }

            for (Direction direction : directions) {

                int xDirection = x + direction.dX;
                int yDirection = y + direction.dY;
                int xDirection2 = x + direction.dX * 2;
                int yDirection2 = y + direction.dY * 2;

                if (0 <= xDirection2 && xDirection2 < level.getSizeX() && 0 <= yDirection2 && yDirection2 < level.getSizeY()) {

                    for (GameObject obj1 : level.get(xDirection, yDirection)) {

                        if (obj1.getMaterial() == Material.Is) {

                            for (GameObject obj2 : level.get(xDirection2, yDirection2)) {

                                Material material2 = obj2.getMaterial();

                                // On verifie que l'objet2 est bien soit un objet règle ou soit un objet text.
                                if (material2.hasEffect() || material2.hasNameObject()) {
                                    if (xDirection2 < x || yDirection2 < y) {
                                        createRule(obj2, obj1, object, level);
                                    } else {
                                        createRule(object, obj1, obj2, level);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Regarde les objets autour d'un objet Is, pour voir si des règles peuvent être formées.
     * @param is Objet "is"
     * @param level Le niveau
     */
    private static void ruleIs(GameObject is, Level level) {
        int x = is.getX();
        int y = is.getY();

        // Regarde les objets à gauche et à droite, pour essayer de former des règles.
        if (1 <= x && x <= level.getSizeX() - 2) {
            for (GameObject object1 : level.get(x - 1, y)) {

                if (object1.getMaterial().hasNameObject()) {

                    for (GameObject object2 : level.get(x + 1, y)) {

                        Material material2 = object2.getMaterial();

                        if (material2.hasNameObject() || material2.hasEffect()) {
                            createRule(object1, is, object2, level);
                        }
                    }
                }
            }
        }

        // Regarde les objets en haut et en bas, pour essayer de former des règles.
        if (1 <= y && y <= level.getSizeY() - 2) {
            for (GameObject object1 : level.get(x, y - 1)) {

                if (object1.getMaterial().hasNameObject()) {

                    for (GameObject object2 : level.get(x, y + 1)) {

                        Material material2 = object2.getMaterial();

                        if (material2.hasNameObject() || material2.hasEffect()) {
                            createRule(object1, is, object2, level);
                        }
                    }
                }
            }
        }
    }

    // Permet de vérifier si une règle est toujours valide.
    private boolean stillValid(Level level) {
        int x = is.getX();
        int y = is.getY();

        if (!level.get(x, y).contains(is)) {
            return false;
        }

        if (0 <= x - 1 && x + 1 < level.getSizeX()) {
            if (level.get(x - 1, y).contains(obj1) && level.get(x + 1, y).contains(obj2))
                return true;
        }

        if (0 <= y - 1 && y + 1 < level.getSizeY()) {
            if (level.get(x, y - 1).contains(obj1) && level.get(x, y + 1).contains(obj2))
                return true;
        }
        return false;
    }

    // Permet de d'annuler une règle.
    private void unRule(Level level) {

        obj1.getAssociatedRules().remove(this);
        obj2.getAssociatedRules().remove(this);
        is.getAssociatedRules().remove(this);
        level.getRules().remove(this);

        // Permet d'assombrir les objets qui ne forme plus de règle.
        ColorAdjust brightnessAdjust = GameObject.getBrightnessAdjust();

        if (obj1.getAssociatedRules().isEmpty()) {
            obj1.getIv().setEffect(brightnessAdjust);
        }

        if (obj2.getAssociatedRules().isEmpty()) {
            obj2.getIv().setEffect(brightnessAdjust);
        }

        if (is.getAssociatedRules().isEmpty()) {
            is.getIv().setEffect(brightnessAdjust);
        }

        // Permet de retirer les effets au objets.
        if (obj2.getMaterial().hasEffect()) {

            ArrayList<GameObject> objects = level.getInstances().get(material1);

            Effect effect = obj2.getMaterial().getEffect();

            for (GameObject object : objects) {
                object.removeTag(effect, level);
            }
        }
    }

    /**
     * Getter de obj2
     * @return Renvoie l'obj2 qui est soit un text ou soit un effet
     */
    public GameObject getObj2() {
        return obj2;
    }

    /**
     * Getter de material1
     * @return Renvoie le material de l'obj1
     */
    public Material getMaterial1() {
        return material1;
    }

    /**
     * Getter de material2
     * @return Renvoie le material de l'obj2
     */
    public Material getMaterial2() {
        return material2;
    }

    /**
     * Getter de effect
     * @return Renvoie l'effet
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Permet de savoir si la règle contient un material2.
     * @return True, si material2 est différent de null, false sinon.
     */
    public boolean hasMaterial2() {
        return !(material2 == null);
    }

}
