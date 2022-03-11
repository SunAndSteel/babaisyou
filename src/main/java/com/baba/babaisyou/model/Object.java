package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effects;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Object {
    private int x;
    private int y;
    private Material material;

    public static Map<Material, ArrayList<Object>> instances = createInstancesMap();

    public static Map<Material, ArrayList<Object>> createInstancesMap() {
        Map<Material, ArrayList<Object>> instances = new HashMap<Material, ArrayList<Object>>();
        for (Material material : Material.values()) {
            instances.put(material, new ArrayList<Object>());
        }
        return instances;
    }

    public Object(Material material, int x, int y) {
        this.x = x; this.y = y;
        this.material = material;
        instances.get(material).add(this);
    }

    public Object(String materialName, int x, int y) {
        this.x = x; this.y = y;
        material = Material.valueOf(materialName);
        instances.get(this.material).add(this);
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public Material getMaterial() { return material; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMaterial(Material material) {
        instances.get(this.material).remove(this);
        instances.get(material).add(this);
        this.material = material;
    }

    public void setMaterial(String materialName) {
        Material material = Material.valueOf(materialName);
        instances.get(this.material).remove(this);
        instances.get(material).add(this);
        this.material = material;
    }

    public void move(Direction direction) {
        if (!this.isMovable(direction))
            return;

        int dX = direction.dX; int dY = direction.dY;
        int x = this.x + dX; int y = this.y + dY;
        ArrayOfObject[][] grid = Grid.getInstance().grid;
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
            }
        }

        grid[this.y][this.x].remove(this);
        grid[y][x].add(this);
        this.x = x; this.y = y;
    }


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

//    public Object copyObject() {
//        return new Object(this.material, this.x, this.y);
//    }

}
