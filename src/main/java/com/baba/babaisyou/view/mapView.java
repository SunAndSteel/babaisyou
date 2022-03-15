package com.baba.babaisyou.view;

import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.presenter.Grid;

import java.util.ArrayList;

/**
 * Classe qui représente la map de manière graphique
 */
public class mapView {
    /**
     * Affiche la map dans un terminal
     */
    public static void printMap() {
        ArrayList<Object>[][] map = Grid.getInstance().grid;

        for (ArrayList<Object>[] row : map) {
            for (ArrayList<Object> objects : row) {
                Object object = objects.get(objects.size() - 1);
                System.out.print(object.getMaterial());
            }
            System.out.println();
        }
    }
}
