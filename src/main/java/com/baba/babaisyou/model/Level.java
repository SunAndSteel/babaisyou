package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

/**
 * Classe permettant de gérer un niveau.
 */
public class Level implements Iterable<ArrayList<GameObject>> {

    private final ArrayList<Rule> rules = new ArrayList<>();

    private int sizeX, sizeY;
    private ArrayList<GameObject>[][] levelGrid;
    private final Map<Material, ArrayList<GameObject>> instances = createInstancesMap();
    private final Stack<Map<GameObject, Direction>> reverseStack = new Stack<>();
    private boolean win;
    private boolean loose;

    /**
     * Construction de level
     * @param name Le nom du niveau à charger.
     */
    public Level(String name) {

        try {
            Mouvement.getMovedObjects().clear();
            LevelLoader.loadLevel("src/main/resources/com/baba/babaisyou/levels/" + name + ".txt", this);
            Rule.checkAllRules(this);
        } catch (IOException | FileNotInCorrectFormat e) {
            levelGrid = null;
        }

    }

    /**
     * Constructeur de level
     * @param name Le nom du niveau à charger.
     * @param path Le path du dossier où se trouve le niveau.
     */
    public Level(String name, String path) {

        try {
            Mouvement.getMovedObjects().clear();
            LevelLoader.loadLevel(path + name + ".txt", this);
            Rule.checkAllRules(this);
        } catch (IOException | FileNotInCorrectFormat e) {
            levelGrid = null;
        }

    }

    /**
     * Setter de win
     * @param win La nouvelle valeur de win.
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * Getter de win
     * @return La valeur de win.
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Setter de loose
     * @param loose La nouvelle valeur de loose.
     */
    public void setLoose(boolean loose) {
        this.loose = loose;
    }

    /**
     * Getter de loose
     * @return La valeur de loose.
     */
    public boolean isLoose() {
        return loose;
    }

    /**
     * Permet de mettre le levelGrid.
     * @param levelGrid La nouvelle valeur de levelGrid
     */
    public void setLevelGrid(ArrayList<GameObject>[][] levelGrid) {
        this.levelGrid = levelGrid;
    }

    /**
     * Getter de levelGrid
     * @return Le tableau à deux dimensions d'ArrayList.
     */
    public ArrayList<GameObject>[][] getLevelGrid() {
        return levelGrid;
    }

    /**
     * Getter de instances.
     * @return Les dictionnaires d'instances.
     */
    public Map<Material, ArrayList<GameObject>> getInstances() {
        return instances;
    }

    /**
     * Getter de reverseStack
     * @return La pile de dictionnaire permettant de revenir en arrière.
     */
    public Stack<Map<GameObject, Direction>> getReverseStack() {
        return reverseStack;
    }

    /**
     * Crée le dictionnaire instances.
     * @return Le dictionnaire instances.
     */
    public static Map<Material, ArrayList<GameObject>> createInstancesMap() {

        Map<Material, ArrayList<GameObject>> instances = new HashMap<>();

        for (Material material : Material.values()) {
            instances.put(material, new ArrayList<>());
        }
        return instances;
    }

    /**
     * Getter de la taille X
     * @return La taille X du level
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Getter de la taille Y
     * @return La taille Y du level
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Setter de la taille X.
     * @param sizeX La nouvelle taille X.
     */
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    /**
     * Setter de la taille Y.
     * @param sizeY La nouvelle taille Y.
     */
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    /**
     * Getter de rules
     * @return La liste des règles du niveau.
     */
    public ArrayList<Rule> getRules() {
        return rules;
    }

    /**
     * Permet de récupérer la liste d'objet à l'endroit x, y.
     * @param x La position x.
     * @param y La position y.
     * @return Une arraylist d'objets à la position (x, y).
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

        /**
         * Constructeur de LevelIterator
         * @param levelInstance Le niveau
         */
        public LevelIterator(Level levelInstance) {
            this.levelInstance = levelInstance;
        }

        /**
         * Permet de vérifier s'il y a encore au moins un objet dans le tableau en 2d.
         * @return Retourne vrai s'il y a encore au moins un objet dans le tableau en 2d.
         */
        @Override
        public boolean hasNext() {
            return x >= 0 && x < levelInstance.getSizeX() && y >= 0 && y < levelInstance.getSizeY();
        }

        /**
         * Permet de récupérer le prochain objet.
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

    /**
     * Permet de retirer un objet du niveau.
     * @param x la position x de l'objet
     * @param y la position y de l'objet
     * @param object L'objet à retirer.
     */
    public void removeObject(int x, int y, GameObject object) {
        get(x, y).remove(object);
        instances.get(object.getMaterial()).remove(object);
        Mouvement.getMovedObjects().put(object, Direction.NONE);
    }

    /**
     * Permet d'ajouter un objet dans le niveau.
     * @param x la position x de l'objet
     * @param y la position y de l'objet
     * @param object L'objet à ajouter.
     */
    public void addObject(int x, int y, GameObject object) {
        get(x, y).add(object);
        instances.get(object.getMaterial()).add(object);
        Mouvement.getMovedObjects().put(object, Direction.NONE);
    }
}
