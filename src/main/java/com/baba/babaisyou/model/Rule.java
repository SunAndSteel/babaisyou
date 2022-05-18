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
                object.getTags().add(materialObj2.getEffect());
            }

        } else {

            Material newMaterial = Material.valueOf(materialObj2.getNameObject());

            for (GameObject object : instances) {
                object.setMaterial(newMaterial, level);
            }
        }
    }

    /**
     * Permet de construire les règles
     * @param obj1 Un objet de la map
     * @param obj2 Un objet de la map
     */
    public static void createRule(GameObject obj1, GameObject is, GameObject obj2, Level level) {

        Material mat1 = obj1.getMaterial();
        Material mat2 = obj2.getMaterial();

        if (!mat1.hasNameObject() || (!mat2.hasNameObject() && !mat2.hasEffect())) {
            return;
        }


        for (Rule rule : level.getRules()) {
            if (rule.obj1 == obj1 && rule.obj2 == obj2) {
                return;
            }
        }

        new Rule(obj1, is, obj2, level);

    }

    /**
     * Parcours la grille pour chercher les règles
     */
    public static void checkAllRules(Level level) {

        for (GameObject is : level.getInstances().get(Material.Is)) {
            isRule(is, level);
        }
    }

    /**
     * Recherche les règles du niveau
     * @param level Le niveau
     */
    public static void checkRules(Level level) {
        Map<GameObject, Direction> movedObjects = Mouvement.getMovedObjects();

        for (GameObject object : movedObjects.keySet()) {

            ArrayList<Rule> associatedRules = new ArrayList<>(object.getAssociatedRules());

            for (Rule rule : associatedRules) {
                if (!rule.stillValid(level)) {
                    rule.unrule(level);
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
                isRule(object, level);
                continue;

            } else if (material.hasNameObject()) {
                directions = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

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
     * Détermine si une règle est bien formée
     * @param is Objet "is"
     * @param level Le niveau
     */
    private static void isRule(GameObject is, Level level) {
        int x = is.getX();
        int y = is.getY();

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

    private void unrule(Level level) {
        obj1.getAssociatedRules().remove(this);
        obj2.getAssociatedRules().remove(this);
        is.getAssociatedRules().remove(this);
        level.getRules().remove(this);

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


        if (obj2.getMaterial().hasEffect()) {

            ArrayList<GameObject> objects = level.getInstances().get(material1);

            Effect effect = obj2.getMaterial().getEffect();

            for (GameObject object : objects) {
                object.getTags().remove(effect);
            }
        }
    }

    /**
     * @return Renvoie l'effet
     */
    public GameObject getObj2() {
        return obj2;
    }

    /**
     * @return Renvoie le material affecté par la règle
     */
    public Material getMaterial1() {
        return material1;
    }

    /**
     * @return Renvoie le material règle
     */
    public Material getMaterial2() {
        return material2;
    }

    /**
     * @return Renvoie l'effet
     */
    public Effect getEffect() {
        return effect;
    }

    public boolean hasMaterial2() {
        return !(material2 == null);
    }

}
