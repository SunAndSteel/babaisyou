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

        // Permet d'initialiser l'affichage de l'objet, grâce à la fonction drawMovedObjects.
        Mouvement.getMovedObjects().put(this, Direction.NONE);
    }

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

        for (Rule rule : level.getRules()) {
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
