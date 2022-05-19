package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.view.MapView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe représentant un objet
 */
public class GameObject implements Comparable<GameObject> {
    private int x, y;
    private Material material;
    private boolean reverse;
    private GameObject best;

    private final ArrayList<Effect> tags = new ArrayList<>();
    private final ImageView iv;
    private final ArrayList<Rule> associatedRules = new ArrayList<>();

    private static final ColorAdjust brightnessAdjust = new ColorAdjust(0, 0, -0.6, 0);
    private static final Glow glowEffect = new Glow(0.2);

    /**
     * Constructeur d'un objet de la map.
     * @param material Le matériel de l'objet.
     * @param x La position x.
     * @param y La position y.
     */
    public GameObject(Material material, int x, int y) {
        this.x = x; this.y = y;
        this.material = material;

        iv = new ImageView(material.getImages()[0]);
        iv.setPreserveRatio(true);
        iv.setFitHeight(MapView.getTileSize());

        // Si c'est un objet text ou effet, on lui donne automatiquement le tag pour pouvoir les bouger.
        if (material.hasEffect() || material.hasNameObject() || material == Material.Is) {
            tags.add(Effect.Movable);
            iv.setEffect(brightnessAdjust);
        }

        // Permet d'initialiser l'affichage de l'objet, grâce à la fonction drawMovedObjects.
        Mouvement.getMovedObjects().put(this, Direction.NONE);
    }

    /**
     * Getter de x.
     * @return La position x de l'objet.
     */
    public int getX() { return x; }

    /**
     * Setter de x.
     * @param x La nouvelle position x de l'objet.
     */
    public void setX(int x) { this.x = x; }

    /**
     * Getter de y.
     * @return La position y de l'objet.
     */
    public int getY() { return y; }

    /**
     * Setter de y.
     * @param y La nouvelle position y de l'objet.
     */
    public void setY(int y) { this.y = y; }

    /**
     * Setter de reverse.
     * @param reverse La nouvelle valeur de reverse.
     */
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    /**
     * Getter de reverse.
     * @return La valeur de reverse.
     */
    public boolean getReverse() {
        return reverse;
    }

    /**
     * Getter de material.
     * @return Le matériel de l'objet
     */
    public Material getMaterial() { return material; }

    /**
     * Getter des tags de l'objet.
     * @return Les tags de l'objet.
     */
    public ArrayList<Effect> getTags() {
        return tags;
    }

    /**
     * Getter de iv
     * @return L'imageView de l'objet.
     */
    public ImageView getIv() {
        return iv;
    }

    /**
     * Getter de associatedRules.
     * @return La liste des règles associer à l'objet.
     */
    public ArrayList<Rule> getAssociatedRules() {
        return associatedRules;
    }

    /**
     * Permet à l'objet de changer de materiel
     * @param material Un nouveau materiel.
     * @param level Le niveau.
     */
    public void setMaterial(Material material, Level level) {

        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

        instances.get(this.material).remove(this);
        instances.get(material).add(this);
        this.material = material;
        iv.setImage(material.getImages()[0]);

        // On clear les effets
        if (tags.contains(Effect.Best)) {
            removeTag(Effect.Best, level);
        }
        tags.clear();

        // On vérifie les règles existantes pour voir s'il ne faut pas lui ajouter des tags.
        for (Rule rule : level.getRules()) {
            if (rule.getMaterial1() == material) {

                if (rule.hasMaterial2()) {
                    setMaterial(rule.getMaterial2(), level);
                } else {
                    addTag(rule.getEffect(), level);
                }
            }
        }
    }

    /**
     * Permet de comparer deux objets.
     * @param object L'objet qu'on compare.
     * @return Retourne 1 si this se trouve en premier dans le niveau ou au même endroit, -1 sinon.
     */
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

    /**
     * Getter de brightnessAdjust.
     * @return Le brightnessAdjust.
     */
    public static ColorAdjust getBrightnessAdjust() {
        return brightnessAdjust;
    }

    /**
     * Getter de glowEffect.
     * @return Le glowEffect.
     */
    public static Glow getGlowEffect() {
        return glowEffect;
    }

    /**
     * Permet d'ajouter un tag à l'objet.
     * @param effect Le tag.
     * @param level Le niveau.
     */
    public void addTag(Effect effect, Level level) {
        tags.add(effect);

        if (effect == Effect.Best) {
            best = new GameObject(Material.BestObject, x, y);
            level.addObject(x, y, best);
        }
    }

    /**
     * Permet de retirer un tag de l'objet.
     * @param effect Le tag à retirer.
     * @param level Le niveau.
     */
    public void removeTag(Effect effect, Level level) {
        tags.remove(effect);

        if (effect == Effect.Best && best != null) {
            level.removeObject(x, y, best);
            best = null;
        }
    }

    /**
     * Getter de best
     * @return L'objet de best.
     */
    public GameObject getBest() {
        return best;
    }
}
