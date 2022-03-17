package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effects;

import java.util.ArrayList;

/**
 * La classe qui représente la map
 */
public class Grid {
    public ArrayOfObject[][] grid;
    private static Grid instance = null;
    private boolean win = false;

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
        Object.instances = Object.createInstancesMap();
        grid = Level.loadlevel(levelNbr);
    }

    public void mapLoadLevel(String name) {
        Rule.objectsAffectedByRules = Rule.createObjectsAffectedByRulesMap();
        Object.instances = Object.createInstancesMap();
        grid = Level.loadlevel(name);
    }


    /**
     * Permet de bouger tous les joueurs (si possible) dans une certaine direction.
     * @param direction Direction dans laquelle les joueurs vont bouger.
     */
    public void movePlayers(Direction direction) {
        ArrayList<Object> players = Rule.objectsAffectedByRules.get(Effects.Player);

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
}