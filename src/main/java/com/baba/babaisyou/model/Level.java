package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Level implements Iterable<ArrayList<GameObject>> {

    private int sizeX, sizeY;
    private ArrayList<GameObject>[][] levelGrid;
    private final Map<Material, ArrayList<GameObject>> instances = createInstancesMap();
    private final Stack<Map<GameObject, Direction>> reverseStack = new Stack<>();
    private final String fileName;
    private boolean isNewLevel = true;

    private static int currentLevelNbr;


    public Level(int levelNbr) throws IOException, FileNotInCorrectFormat {
        currentLevelNbr = levelNbr;
        LevelLoader.loadLevel("level" + levelNbr, this);
        fileName = "level" + levelNbr;
    }

    public Level(String name) throws IOException, FileNotInCorrectFormat {
        LevelLoader.loadLevel(name, this);
        fileName = name;
    }

    public  Level(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        fileName = null;
        LevelLoader.createEmptyGrid(sizeX, sizeY, this);
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
     * @return Le numéro du level actuel
     */
    public static int getCurrentLevelNbr() {
        return currentLevelNbr;
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

    /**
     * @param x La position x de l'objet
     * @param y La position y de l'objet
     * @return Une arraylist d'objets à la position (x, y)
     */
    public ArrayList<GameObject> get(int x, int y) {
        return levelGrid[y][x];
    }

    public ArrayList<GameObject> get(Point point) {
        return  levelGrid[point.y][point.x];
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

    public boolean isNewLevel() {
        return isNewLevel;
    }

    public void setIsNewLevel(boolean newLevel) {
        isNewLevel = newLevel;
    }
}
