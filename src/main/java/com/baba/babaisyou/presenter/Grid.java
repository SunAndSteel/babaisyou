package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effects;
import com.baba.babaisyou.view.LevelView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe qui représente la map
 */
public class Grid {
    public ArrayOfObject[][] grid;
    private static Grid instance = null;
    private boolean win = false;
//    private ArrayList<ArrayOfObject[][]> oldGrids = new ArrayList<>();
    private ArrayList<Map<Object, Direction>> reverseList = new ArrayList<>();

    // Constructeur de la class Grid. Permet de charger le premier level. Il est appelé une seule fois car la class
    // est un singleton.
    private Grid() {
        mapLoadLevel(0);
    }

    /**
     * Permet de créer une instance de Grid, s'il n'en existe pas, sinon il retourne l'instance déjà créé.
     * @return Retourne la seule instance de la class Grid.
     */
    public static Grid getInstance() {
        if (instance == null)
            instance = new Grid();
        return instance;
    }

    /**
     * Permet de charger un level dans la variable d'instance grid.
     * @param levelNbr Numero du level à load.
     */
    public void mapLoadLevel(int levelNbr) {
        Rule.objectsAffectedByRules = Rule.createObjectsAffectedByRulesMap();
        Object.resetInstancesMap();
        grid = Level.loadlevel(levelNbr);
    }

    public void mapLoadLevel(String name) {
        Rule.objectsAffectedByRules = Rule.createObjectsAffectedByRulesMap();
        Object.resetInstancesMap();
        grid = Level.loadlevel(name);
    }


    /**
     * Permet de bouger tous les joueurs (si possible) dans une certaine direction.
     * @param direction Direction dans laquelle les joueurs vont bouger.
     */
    public void movePlayers(Direction direction) {
        ArrayList<Object> players = Rule.objectsAffectedByRules.get(Effects.Player);

        // la liste de player n'est pas rangé du coup bug

        Collections.sort(players);

        if (direction == Direction.DOWN || direction == Direction.RIGHT) {
            for (int i = players.size() - 1; i >=0; i--) {
                Object player = players.get(i);
                player.move(direction);
            }
        } else {
            for (Object player : players) {
                player.move(direction);
            }
        }
        reverseList.add(Object.getMovedObjects());
        Object.resetMovedObjects();
    }

    /**
     * Vérifie si la variable d'instance win est true, si oui, le dictionaire des instances d'objets est réinitialisés
     * et le prochain level est chargé.
     */
    public void checkWin() {
        if (win) {
            mapLoadLevel(Level.getCurrentLevelNbr() + 1);
            win = false;
        }
    }

    /**
     * Permet de changer l'état de la variable d'instance win.
     * @param state Booléen qui sera attribué la variable d'instance win.
     */
    public void setWin(boolean state) {
        win = state;
    }

    /**
     * Permet de revenir en arrière d'une étape.
     */
    public void reverse() {
        if (reverseList.size() == 0)
            return;

        Map<Object, Direction> lastMovedObjects = reverseList.remove(reverseList.size() - 1);

        for (Object o : lastMovedObjects.keySet()) {

            int x = o.getX();
            int y = o.getY();

            grid[y][x].remove(o);

            Direction direction = lastMovedObjects.get(o);

            int newX = x - direction.dX;
            int newY = y - direction.dY;

            grid[newY][newX].add(o);

            o.setX(newX);
            o.setY(newY);

            if (!Object.getInstances().get(o.getMaterial()).contains(o)) {
                Object.getInstances().get(o.getMaterial()).add(o);
            }
        }
        LevelView.drawAll();
    }
}