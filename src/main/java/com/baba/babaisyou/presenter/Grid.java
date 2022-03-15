package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effects;

/**
 *
 */
public class Grid {
    public ArrayOfObject[][] grid;
    private static Grid instance = null;
    private boolean win = false;

    // Constructeur de la class Grid. Permet de charger le premier level. Il est appelé une seule fois car la class
    // est un singleton.
    private Grid() {
        mapLoadLevel(1);
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
        grid = Level.loadlevel(levelNbr);
    }

    /**
     * Permet de bouger tout les joueurs (si possible) dans une certaine direction.
     * @param direction Direction dans laquelle les joueurs vont bouger.
     */
    public void movePlayers(Direction direction) {
        for (Object player : Rule.objectsAffectedByRules.get(Effects.Player)) { // Pas la bonne solutions s'il y a des players l'un a coté de l'autre
            player.move(direction);
        }
    }

    /**
     * Vérifie si la variable d'instance win est true, si oui, le dictionaire des instance d'objets est réinitialiser
     * et le prochain level est charger.
     */
    public void checkWin() {
        if (win) {
            Object.instances = Object.createInstancesMap();
            mapLoadLevel(Level.getCurrentLevelNbr() + 1);
            win = false;
        }
    }

    /**
     * Permet de changer l'etat de la variable d'instance win.
     * @param state Booléen qui sera attribué la variable d'instance win.
     */
    public void setWin(boolean state) {
        win = state;
    }
}