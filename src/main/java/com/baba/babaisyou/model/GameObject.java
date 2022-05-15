package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.view.LevelView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe représentant un objet sur la map
 */
public class GameObject implements Comparable<GameObject> {
    private int x, y;
    private Material material;
    private boolean reverse;
//  private static Map<GameObject, Direction> movedObjects = new LinkedHashMap<>();
//  private static Map<Material, ArrayList<GameObject>> instances = createInstancesMap();
    private final ArrayList<Effect> tags = new ArrayList<>();
    private final ImageView iv;
    private final ArrayList<Rule> associatedRules = new ArrayList<>();
    private static final ColorAdjust brightnessAdjust = new ColorAdjust(0, 0, -0.6, 0);
    private static final Glow glowEffect = new Glow(0.2);

//    /**
//     * Créé une instance de la map
//     * @return L'instance de la map
//     */
//    public static Map<Material, ArrayList<GameObject>> createInstancesMap() {
//        Map<Material, ArrayList<GameObject>> instances = new HashMap<>();
//        for (Material material : Material.values()) {
//            instances.put(material, new ArrayList<>());
//        }
//        return instances;
//    }
//
//    /**
//     * Réinitialise le dictionnaire instances.
//     */
//    public static void resetInstancesMap() {
//        instances = createInstancesMap();
//    }

    /**
     * Constructeur d'un objet de la map
     * @param material La texture
     * @param x La position x
     * @param y La position y
     */
    public GameObject(Material material, int x, int y) {
        this.x = x; this.y = y;
        this.material = material;
//        instances.get(material).add(this);
        iv = new ImageView(material.getFrames()[0]);
        iv.setPreserveRatio(true);
        iv.setFitHeight(LevelView.getTileSize());

        if (material.hasEffect() || material.hasNameObject() || material == Material.Is) {
            tags.add(Effect.Movable);
            iv.setEffect(brightnessAdjust);
        }

//        if (material.hasEffect() || material.hasNameObject() || material == Material.Is) {
//            tags.add(Effect.Movable);
//        }

//        addMovedObjects(this, Direction.NONE);

        // Permet d'initialiser l'affichage de l'objet, grâce à la fonction drawMovedObjects.
        Mouvement.getMovedObjects().put(this, Direction.NONE);
    }

//    /**
//     * @return Le dictionnaire des instances en fonction de leur matériel
//     */
//    public static Map<Material, ArrayList<GameObject>> getInstances() {
//        return instances;
//    }

    /**
     * @return La position x de l'objet
     */
    public int getX() { return x; }

    /**
     * @param x La nouvelle position x de l'objet
     */
    public void setX(int x) { this.x = x; }

    /**
     * @return La position y de l'objet
     */
    public int getY() { return y; }

    /**
     * @param y La nouvelle position y de l'objet
     */
    public void setY(int y) { this.y = y; }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean getReverse() {
        return reverse;
    }

    /**
     * @return La texture de l'objet
     */
    public Material getMaterial() { return material; }

    public ArrayList<Effect> getTags() {
        return tags;
    }

    public ImageView getIv() {
        return iv;
    }

    public ArrayList<Rule> getAssociatedRules() {
        return associatedRules;
    }

    /**
     * @param material Un objet material
     */
    public void setMaterial(Material material, Level level) {

        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

        instances.get(this.material).remove(this);
        instances.get(material).add(this);
        this.material = material;
        iv.setImage(material.getFrames()[0]);

        for (Rule rule : Rule.getRules()) {
            if (rule.getMaterial1() == material) {

                if (rule.hasMaterial2()) {
                    setMaterial(rule.getMaterial2(), level);
                } else {
                    tags.add(rule.getEffect());
                }
            }
        }

//        addMovedObjects(this, Direction.NONE);

    }

//    /**
//     * Bouge l'objet si c'est possible
//     * @param direction La direction vers laquelle on veut bouger
//     */
//    public void move(Direction direction) {
//        if (!this.isMovable(direction))
//            return;
//
//        int dX = direction.dX; int dY = direction.dY;
//        int newX = x + dX; int newY = y + dY;
//        Game game = Game.getInstance();
//        Level level = game.getLevel();
//
//
//        // Itérer dans ce sens permet de bouger par exemple : s'il y a plusieurs objets movable
//        // aux mêmes endroits, alors on bouge d'abord l'objet qui est derrière l'autre dans la liste (et donc qui est affiché devant l'autre lors du print)
//        for (int i = (level.get(newX, newY).size() - 1); i >= 0; i--) {
//            GameObject object = level.get(newX, newY).get(i);
//
//            ArrayList<Effect> tags = object.getTags();
//
//            if (tags.contains(Effect.Movable)) {
//                object.move(direction);
//
//            } else if (tags.contains(Effect.Killer)) {
//                level.get(x, y).remove(this);
//                instances.get(this.getMaterial()).remove(this);
//                addMovedObjects(this, Direction.NONE);
//                return;
//
//            } else if (tags.contains(Effect.Winner) &&
//                    this.getTags().contains(Effect.Player)) {
//
//                game.setWin(true);
//            } else if (tags.contains(Effect.Play)) {
//                game.mapLoadLevel(Level.getCurrentLevelNbr() + 1); //mauvais idée car si il reste encore un joueur qui n'a pas encore bouger, après le loadlevel, il va être bouger et donc venir dans la mauvaise map
//            }
//        }
//
//        addMovedObjects(this, direction);
//
//        level.get(x, y).remove(this);
//        level.get(newX, newY).add(this);
//        x = newX; y = newY;
//    }

//    /**
//     * Vérifie si on peut bouger l'objet dans une direction
//     * @param direction La direction
//     * @return Vrai si on peut bouger dans la direction, sinon faux
//     */
//    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
//    private boolean isMovable(Direction direction) {
//        int dX = direction.dX; int dY = direction.dY;
//        int newX = x + dX; int newY = y + dY;
//        Level grid = Game.getInstance().getLevel();
//
//        if ((0 > newX || newX > grid.getSizeX() - 1) || (0 > newY || newY > grid.getSizeY() - 1))
//            return false;
//
//        for (GameObject object : grid.get(newX, newY)) {
//
//            ArrayList<Effect> tags = object.getTags();
//
//            if (tags.contains(Effect.Player)) {
//                return false;
//
//            } else if (tags.contains(Effect.Movable)) {
//                if (!object.isMovable(direction))
//                    return false;
//
//            } else if (tags.contains(Effect.Hittable)) {
//                return false;
//            }
//        }
//        return true;
//    }

//    /**
//     * Sauvegarde dans une liste un object qui a bougé
//     * @param object L'object à ajouter dans la liste
//     */
//    public static void addMovedObjects(GameObject object, Direction direction) {
//        movedObjects.put(object, direction);
//    }
//
//    /**
//     *
//     * @return La liste des objects qui ont bougé
//     */
//    public static Map<GameObject, Direction> getMovedObjects() {
//        return movedObjects;
//    }
//
//    /**
//     * Vide la liste où est sauvegardé les objects qui ont bougé
//     */
//    public static void resetMovedObjects() {
//
//        // C'est important de ne pas clear à la place.
//        movedObjects = new LinkedHashMap<>();
//    }

    @Override
    public int compareTo(GameObject object) {
        if (this.y > object.y) {
            return 1;
        } else if (this.y == object.y) {
            if (this.x >= object.x) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static ColorAdjust getBrightnessAdjust() {
        return brightnessAdjust;
    }

    public static Glow getGlowEffect() {
        return glowEffect;
    }
}
