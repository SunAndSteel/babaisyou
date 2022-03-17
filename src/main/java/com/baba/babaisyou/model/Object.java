package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effects;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant un objet sur la map
 */
public class Object {
    private int x;
    private int y;
    private Material material;
    private static ArrayList<Position> movedPos = new ArrayList<>(); // Les endroits où des objets ont été bouger.

    public static Map<Material, ArrayList<Object>> instances = createInstancesMap();

    /**
     * Créé une instance de la map
     * @return L'instance de la map
     */
    public static Map<Material, ArrayList<Object>> createInstancesMap() {
        Map<Material, ArrayList<Object>> instances = new HashMap<>();
        for (Material material : Material.values()) {
            instances.put(material, new ArrayList<>());
        }
        return instances;
    }

    /**
     * Constructeur d'un objet de la map
     * @param material La texture
     * @param x La position x
     * @param y La position y
     */
    public Object(Material material, int x, int y) {
        this.x = x; this.y = y;
        this.material = material;
        instances.get(material).add(this);
        addMovedPos(new Position(x, y));
    }

    /**
     * Constructeur
     * @param materialName Le nom de la texture
     * @param x La position x
     * @param y La position y
     */
    public Object(String materialName, int x, int y) {
        this.x = x; this.y = y;
        material = Material.valueOf(materialName);
        instances.get(this.material).add(this);
        addMovedPos(new Position(x, y));
    }

    /**
     * @return La position x de l'objet
     */
    public int getX() { return x; }

    /**
     * @return La position y de l'objet
     */
    public int getY() { return y; }

    /**
     * @return La texture de l'objet
     */
    public Material getMaterial() { return material; }

    /**
     * @param x Position x de l'objet
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y Position y de l'objet
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @param material Un objet material
     */
    public void setMaterial(Material material) {
        instances.get(this.material).remove(this);
        instances.get(material).add(this);
        this.material = material;
    }

    /**
     * @param materialName Le nom du material
     */
    public void setMaterial(String materialName) {
        Material material = Material.valueOf(materialName);
        instances.get(this.material).remove(this);
        instances.get(material).add(this);
        this.material = material;
    }

    /**
     * Bouge l'objet si c'est possible
     * @param direction La direction vers laquelle on veut bouger
     */
    public void move(Direction direction) {
        if (!this.isMovable(direction))
            return;

        int dX = direction.dX; int dY = direction.dY;
        int x = this.x + dX; int y = this.y + dY;
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        Grid gridInstance = Grid.getInstance();
        Map<Effects, ArrayList<Object>> objectsAffectedByRules = Rule.objectsAffectedByRules;


        // Itérer dans ce sens permet de bouger par exemple : s'il y a plusieurs objets mouvable
        // au même endroits, alors on bouge d'abort  l'objet qui est derrière l'autre dans la liste (et donc qui est affichier devant l'autre lors du print)
        for (int i = (grid[y][x].size() - 1); i >= 0; i--) {
            Object object = grid[y][x].get(i);

            if (objectsAffectedByRules.get(Effects.Movable).contains(object)) {
                object.move(direction);

            } else if (objectsAffectedByRules.get(Effects.Killer).contains(object)) {
                grid[this.y][this.x].remove(this);
                return;

            } else if (objectsAffectedByRules.get(Effects.Winner).contains(object) &&
                    objectsAffectedByRules.get(Effects.Player).contains(this)) {

                Grid.getInstance().setWin(true);
                return;
            } else if (objectsAffectedByRules.get(Effects.Play).contains(object)) {
                Grid.getInstance().mapLoadLevel(Level.getCurrentLevelNbr() + 1);
            }
        }

        addMovedPos(new Position(x, y));
        addMovedPos(new Position(this.x, this.y));
        grid[this.y][this.x].remove(this);
        grid[y][x].add(this);
        this.x = x; this.y = y;
    }

    /**
     * Vérifie si on peut bouger l'objet dans une direction
     * @param direction La direction
     * @return Vrai si on peut bouger dans la direction, sinon faux
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isMovable(Direction direction) {
        int dX = direction.dX; int dY = direction.dY;
        int x = this.x + dX; int y = this.y + dY;
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        Map<Effects, ArrayList<Object>> objectsAffectedByRules = Rule.objectsAffectedByRules;

        if ((0 > x || x > Level.getSizeX() - 1) || (0 > y || y > Level.getSizeY() - 1))
            return false;

        for (Object object : grid[y][x]) {
            if (objectsAffectedByRules.get(Effects.Player).contains(object)) {
                return false;

            } else if (objectsAffectedByRules.get(Effects.Movable).contains(object)) {
                if (!object.isMovable(direction))
                    return false;

            } else if (objectsAffectedByRules.get(Effects.Hittable).contains(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sauvegarde dans une liste l'ancienne et la nouvelle position
     * @param position La position
     */
    public static void addMovedPos(Position position) { // Pour améliorer, on peut ajouter la position, si elle n'est pas déjà dedans.
        movedPos.add(position);
    }

    /**
     *
     * @return La position vers laquelle le joueur a bougé
     */
    public static ArrayList<Position> getMovedPos() {
        return movedPos;
    }

    /**
     * Vide la liste où est sauvegardé l'ancienne et la nouvelle position de l'objet
     */
    public static void resetMovedPos() {
        movedPos = new ArrayList<>();
    }

}
