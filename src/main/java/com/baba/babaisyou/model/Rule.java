package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Effects;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rule {

    public static ArrayList<ArrayList<Object>> Rules = new ArrayList<ArrayList<Object>>();

    public static Map<Effects, ArrayList<Object>> objectsAffectedByRules = createObjectsAffectedByRulesMap();

    private static Map<Effects, ArrayList<Object>> createObjectsAffectedByRulesMap() {
        Map<Effects, ArrayList<Object>> objectsAffectedByRules = new HashMap<Effects, ArrayList<Object>>();
        for (Effects effect : Effects.values()) {
            objectsAffectedByRules.put(effect, new ArrayList<Object>());
        }


        // Ajoute toutes les instances des materiels qui ont un effet dans la liste des instances mouvable car
        // ils sont mouvable sans aucune règle (par exemple : on peut bouger le You sans règle)
        for (Material material : Material.values()) {
            if (material.getEffect() != null || material == Material.Is || material.getNameObjectAffected() != null) {
                for (Object object : Object.instances.get(material)) {
                    objectsAffectedByRules.get(Effects.Movable).add(object);
                }
            }
        }

        return objectsAffectedByRules;

    }

    public static void rule(Object obj1, Object obj2) {
        if (obj2.getMaterial().getEffect() != null && obj1.getMaterial().getNameObjectAffected() != null) {
            for (Object object : Object.instances.get(Material.valueOf(obj1.getMaterial().getNameObjectAffected()))) {
                objectsAffectedByRules.get(obj2.getMaterial().getEffect()).add(object);
            }
        } else if (obj2.getMaterial().getNameObjectAffected() != null && obj1.getMaterial().getNameObjectAffected() != null) {
            ArrayList<Object> instances = new ArrayList<Object>(
                    Object.instances.get(Material.valueOf(obj1.getMaterial().getNameObjectAffected())));

            for (Object object : instances) {
                object.setMaterial(Material.valueOf(obj2.getMaterial().getNameObjectAffected()));
            }
        }
    }



    public static void checkRules() {
        objectsAffectedByRules = createObjectsAffectedByRulesMap();
        ArrayOfObject[][] grid = Grid.getInstance().grid;

        for (Object Is : Object.instances.get(Material.Is)) {
            int x = Is.getX(); int y = Is.getY();

            if (1 <= x && x <= Level.getSizeX() - 2) {
                for (Object object1 : grid[y][x - 1]) {
                    for (Object object2 : grid[y][x + 1]) {
                        rule(object1, object2);
                    }
                }

            }

            if (1 <= y && y <= Level.getSizeY() - 2) {
                for (Object object1 : grid[y - 1][x]) {
                    for (Object object2 : grid[y + 1][x]) {
                        rule(object1, object2);
                    }
                }
            }

        }
    }


}
