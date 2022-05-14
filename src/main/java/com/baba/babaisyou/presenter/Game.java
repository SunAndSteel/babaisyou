package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.Level;

/**
 * La classe qui représente la map
 */
public class Game {
    private Level level;
    private static Game instance = null;
    private boolean win = false;


    /**
     * Permet de créer une instance de Grid, s'il n'en existe pas, sinon il retourne l'instance déjà créé.
     * @return Retourne la seule instance de la class Grid.
     */
    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    /**
     * Permet de charger un level dans la variable d'instance grid.
     * @param levelNbr Numero du level à load.
     */
    public void mapLoadLevel(int levelNbr) {
//        GameObject.resetInstancesMap();
        try {
            level = new Level(levelNbr);

        } catch (Exception e) { // TODO A Changer

        }
//        reverseStack.clear();
        Rule.getRules().clear();
        Rule.checkAllRules(level);
    }

    public void mapLoadLevel(String name){
//        GameObject.resetInstancesMap();
        try {
            level = new Level(name);

        } catch (Exception e) { // TODO A Changer

        }
//        reverseStack.clear();
        Rule.getRules().clear();
        Rule.checkAllRules(level);
    }

//    /**
//     * Permet de bouger tous les joueurs (si possible) dans une certaine direction.
//     * @param direction Direction dans laquelle les joueurs vont bouger.
//     */
//    public void movePlayers(Direction direction) {
////        GameObject.resetMovedObjects();
//        ArrayList<GameObject> players = new ArrayList<>();
//
//        Map<Material, ArrayList<GameObject>> instances = GameObject.getInstances();
//
//        for (Rule rule : Rule.getRules()) {
//            if (rule.getObj2().getMaterial() == Material.You) {
//                players.addAll(instances.get(rule.getMaterial1()));
//            }
//        }
//
//        // la liste de player n'est pas rangé du coup bug
//        Collections.sort(players);
//
//        if (direction == Direction.DOWN || direction == Direction.RIGHT) {
//            for (int i = players.size() - 1; i >=0; i--) {
//                GameObject player = players.get(i);
//                player.move(direction);
//            }
//        } else {
//            for (GameObject player : players) {
//                player.move(direction);
//            }
//        }
//        reverseStack.add(Mouvement.getMovedObjects());
//    }

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


    public void update() {
        checkWin();
        Rule.checkRules(level);
    }

    public Level getLevel() {
        return level;
    }
}