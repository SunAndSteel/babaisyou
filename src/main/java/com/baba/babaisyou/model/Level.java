package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class Level implements Iterable<ArrayList<GameObject>> {

    private final ArrayList<Rule> rules = new ArrayList<>();

    private int sizeX, sizeY;
    private ArrayList<GameObject>[][] levelGrid;
    private final Map<Material, ArrayList<GameObject>> instances = createInstancesMap();
    private final Stack<Map<GameObject, Direction>> reverseStack = new Stack<>();
    private boolean isNewLevel = true;
    private boolean win;
    private boolean loose;

    public Level(String name) {

        try {
            LevelLoader.loadLevel("src/main/resources/com/baba/babaisyou/levels/" + name + ".txt", this);
            Rule.checkAllRules(this);
        } catch (IOException | FileNotInCorrectFormat e) {
            levelGrid = null;
        }

    }

    public Level(String name, String path) {

        try {
            LevelLoader.loadLevel(path + name + ".txt", this);
            Rule.checkAllRules(this);
        } catch (IOException | FileNotInCorrectFormat e) {
            levelGrid = null;
        }

    }



    public Level(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        LevelLoader.createEmptyGrid(sizeX, sizeY, this);
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isLoose() {
        return loose;
    }

    public void setLevelGrid(ArrayList<GameObject>[][] levelGrid) {
        this.levelGrid = levelGrid;
    }

    public ArrayList<GameObject>[][] getLevelGrid() {
        return levelGrid;
    }

    public Map<Material, ArrayList<GameObject>> getInstances() {
        return instances;
    }

    public Stack<Map<GameObject, Direction>> getReverseStack() {
        return reverseStack;
    }

    /**
     * Créé une instance de la map
     * @return L'instance de la map
     */
    public static Map<Material, ArrayList<GameObject>> createInstancesMap() {

        Map<Material, ArrayList<GameObject>> instances = new HashMap<>();

        for (Material material : Material.values()) {
            instances.put(material, new ArrayList<>());
        }
        return instances;
    }

    /**
     * @return La taille X du level
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * @return La taille Y du level
     */
    public int getSizeY() {
        return sizeY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    /**
     * @param x La position x de l'objet
     * @param y La position y de l'objet
     * @return Une arraylist d'objets à la position (x, y)
     */
    public ArrayList<GameObject> get(int x, int y) {
        return levelGrid[y][x];
    }

    /**
     * Permet d'itérer sur des objets de type Level.
     */
    private class LevelIterator implements Iterator<ArrayList<GameObject>> {
        private int x = 0;
        private int y = 0;
        private final Level levelInstance;

        public LevelIterator(Level levelInstance) {
            this.levelInstance = levelInstance;
        }

        /**
         * @return Retourne vrai si il y a encore au moins un objet dans le tableau en 2d
         */
        @Override
        public boolean hasNext() {
            return x >= 0 && x < levelInstance.getSizeX() && y >= 0 && y < levelInstance.getSizeY();
        }

        /**
         * @return Le prochain objet dans le tableau en 2d
         */
        @Override
        public ArrayList<GameObject> next() {

            ArrayList<GameObject> arrayList = levelGrid[y][x];

            x = (x + 1) % levelInstance.getSizeX();
            y = x == 0 ? y + 1 : y;

            return arrayList;
        }
    }

    /**
     * Crée un itérateur pour la class Level
     * @return LevelIterator
     */
    @NotNull
    @Override
    public Iterator<ArrayList<GameObject>> iterator() {
        return new LevelIterator(this);
    }

    public boolean checkLoose() {

        loose = true;

        for (Rule rule : rules) {
            if (rule.getObj2().getMaterial() == Material.You && !instances.get(rule.getMaterial1()).isEmpty()) {
                loose = false;
                break;
            }
        }

        return loose;
    }

    public void removeObject(int x, int y, GameObject object) {
        get(x, y).remove(object);
        instances.get(object.getMaterial()).remove(object);
        Mouvement.getMovedObjects().put(object, Direction.NONE);
    }
}
