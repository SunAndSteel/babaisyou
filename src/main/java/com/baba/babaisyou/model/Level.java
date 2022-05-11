package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Level implements Iterable<ArrayList<GameObject>> {
    private int sizeX, sizeY;
    private ArrayList<GameObject>[][] level;
    private static int currentLevelNbr;

    /**
     * Constructeur chargeant un niveau existant
     * @param levelNbr Numéro du niveau
     */
    public Level(int levelNbr) {
        currentLevelNbr = levelNbr;
        loadLevel("level" + levelNbr);
    }

    /**
     * Constructeur chargeant un niveau existant
     * @param levelNbr Numéro du niveau
     * @param creator Mode édition
     */
    public Level(int levelNbr, boolean creator) {
        currentLevelNbr = levelNbr;
        loadLevel("level" + levelNbr, creator);
    }

    /**
     * Constructeur chargeant un niveau existant
     * @param name Le nom du niveau
     */
    public Level(String name) {
        loadLevel(name);
    }

    /**
     * Constructeur chargeant un niveau existant
     * @param name Le nom du niveau
     */
    public Level(String name, String filename) {
        loadLevel(name, filename);
    }

    /**
     * Constructeur chargeant un niveau existant
     * @param name Nom du niveau
     * @param creator Mode édition
     */
    public Level(String name, boolean creator) {
        loadLevel(name, creator);
    }

    /**
     * Constructeur permettant de construire un niveau vide
     */
    public Level() {
        createEmptyLevel();
    }

    /**
     * Créer une liste en 2 dimensions d'objets représentant la map à partir d'un fichier texte
     * @param name Le numéro du level à charger
     */
    public void loadLevel(String name) {

        String[] size;
        try {
            // Crée le reader pour le level
            BufferedReader br = new BufferedReader(
                    new FileReader("src/main/resources/com/baba/babaisyou/levels/" + name + ".txt"));

            String line;
            if ((line = br.readLine()) != null) {
                //Récupérer la taille de la map dans la première ligne
                size = line.split(" ");
                sizeX = Integer.parseInt(size[0]);
                sizeY = Integer.parseInt(size[1]);

                //Créé une liste en 2 dimensions de la taille de la map
                level = new ArrayList[sizeY][sizeX];
                for (int row = 0; row < level.length; row++) {
                    for (int col = 0; col < level[row].length; col++) {
                        level[row][col] = new ArrayList<>();
                    }
                }

                //Ajoute des objets des floors sur toutes la map
                for (int i = 0; i < level.length; i++) {
                    for (int j = 0; j < level[0].length; j++) {
                        level[i][j].add(new GameObject(Material.Floor, j, i));
                    }
                }

                //Récupère les objets dans chaque ligne et les ajoutes a la liste en 2 dimensions
                while ((line = br.readLine()) != null) {
                    String[] nextObject = line.split(" ");
                    String objectName = nextObject[0];
                    int x = Integer.parseInt(nextObject[1]);
                    int y = Integer.parseInt(nextObject[2]);
                    level[y][x].add(new GameObject(objectName, x, y));
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("File not in the correct format.");
            e.printStackTrace();
        }
    }

    /**
     * Créer une liste en 2 dimensions d'objets représentant la map à partir d'un fichier texte
     * @param name Le numéro du level à charger
     */
    public void loadLevel(String name, String filepath) {

        String[] size;
        try {
            // Crée le reader pour le level
            BufferedReader br = new BufferedReader(
                    new FileReader(filepath));

            String line;
            if ((line = br.readLine()) != null) {
                //Récupérer la taille de la map dans la première ligne
                size = line.split(" ");
                sizeX = Integer.parseInt(size[0]);
                sizeY = Integer.parseInt(size[1]);

                //Créé une liste en 2 dimensions de la taille de la map
                level = new ArrayList[sizeY][sizeX];
                for (int row = 0; row < level.length; row++) {
                    for (int col = 0; col < level[row].length; col++) {
                        level[row][col] = new ArrayList<>();
                    }
                }

                //Ajoute des objets des floors sur toutes la map
                for (int i = 0; i < level.length; i++) {
                    for (int j = 0; j < level[0].length; j++) {
                        level[i][j].add(new GameObject(Material.Floor, j, i));
                    }
                }

                //Récupère les objets dans chaque ligne et les ajoutes a la liste en 2 dimensions
                while ((line = br.readLine()) != null) {
                    String[] nextObject = line.split(" ");
                    String objectName = nextObject[0];
                    int x = Integer.parseInt(nextObject[1]);
                    int y = Integer.parseInt(nextObject[2]);
                    level[y][x].add(new GameObject(objectName, x, y));
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("File not in the correct format.");
            e.printStackTrace();
        }
    }

    /**
     * Créer une liste en 2 dimensions d'objets représentant la map à partir d'un fichier texte
     * @param name Le nom du niveau
     * @param creator Mode édition
     */
    public void loadLevel(String name, boolean creator) {

        String[] size;
        try {
            // Crée le reader pour le level
            BufferedReader br = new BufferedReader(
                    new FileReader("src/main/resources/com/baba/babaisyou/levels/" + name + ".txt"));

            String line;
            if ((line = br.readLine()) != null) {
                //Récupérer la taille de la map dans la première ligne
                size = line.split(" ");
                sizeX = Integer.parseInt(size[0]);
                sizeY = Integer.parseInt(size[1]);

                //Créé une liste en 2 dimensions de la taille de la map
                level = new ArrayList[sizeY][sizeX];
                for (int row = 0; row < level.length; row++) {
                    for (int col = 0; col < level[row].length; col++) {
                        level[row][col] = new ArrayList<>();
                    }
                }

                //Ajoute des objets des floors sur toutes la map
                for (int i = 0; i < level.length; i++) {
                    for (int j = 0; j < level[0].length; j++) {
                        level[i][j].add(new GameObject(Material.Floor, j, i));
                    }
                }

                //Récupère les objets dans chaque ligne et les ajoutes a la liste en 2 dimensions
                while ((line = br.readLine()) != null) {
                    String[] nextObject = line.split(" ");
                    String objectName = nextObject[0];
                    int x = Integer.parseInt(nextObject[1]);
                    int y = Integer.parseInt(nextObject[2]);
                    level[y][x].add(new GameObject(objectName, x, y));
                }
                if(creator) {
                    level[0][0].add(new GameObject(Material.Cursor, 0, 0));
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("File not in the correct format.");
            e.printStackTrace();
        }
    }

    /**
     * Créé un niveau vide
     */
    public void createEmptyLevel() {
        sizeY = 20;
        sizeX = 20;
        level = new ArrayList[sizeY][sizeX];
        for (int row = 0; row < level.length; row++) {
            for (int col = 0; col < level[row].length; col++) {
                level[row][col] = new ArrayList<>();
            }
        }

        //Ajoute des objets des floors sur toutes la map
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[0].length; j++) {
                level[i][j].add(new GameObject(Material.Floor, j, i));
            }
        }
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

    /**
     * Obtenir la position d'un objet a une position spécifique
     * @param x La position x de l'objet
     * @param y La position y de l'objet
     * @return Une arraylist d'objets à la position (x, y)
     */
    public ArrayList<GameObject> get(int x, int y) {
        return level[y][x];
    }

    /**
     * Obtenir la position de l'objet en position p
     * @param p La position de l'objet
     * @return L'objet en position p
     */
    public ArrayList<GameObject> getObjects(Point p) {
        return level[p.y][p.x];
    }

    /**
     * Obtenir la position du curseur
     * @return Renvoie la position du curseur
     */
    public Point getCursorPosition() {
        for (ArrayList<GameObject>[] i: level) {
            for (ArrayList<GameObject> j: i) {
                for (GameObject k: j) {
                    if (k.getMaterial() == Material.Cursor) {
                        return new Point(k.getX(), k.getY());
                    }
                }
            }
        }
        return new Point();
    }

    /**
     * Ajoute un objet au level
     * @param o L'objet a ajouter
     * @param p La position de l'objet
     */
    public void addObject(GameObject o, Point p) {
        level[p.y][p.x].add(o);
    }

    /**
     * Supprime un object du level
     * @param p la position de l'objet
     */
    public void removeObject(Point p) {
        int cnt = 0;
        GameObject toRemove;

        for(GameObject k: level[p.y][p.x]) {
            if(level[p.y][p.x].size() == 3 && (!k.getMaterial().name().equals("Cursor") || !k.getMaterial().name().equals("Floor"))) {
                break;
            }
            cnt++;
        }
        toRemove = level[p.y][p.x].get(cnt);

        GameObject.addMovedObjects(toRemove, Direction.NONE);
        GameObject.removeFromInstance(toRemove);
        level[p.y][p.x].remove(cnt);
    }

    /**
     * Sauvegarde les emplacements des objets dans le bon format
     * @return une Liste contenant des strings de position d'objet
     */
    public ArrayList<String> save() {
        int y = 0;
        int x = 0;
        ArrayList<String> levelArr = new ArrayList<>();
        levelArr.add(getSizeY() + " " +  getSizeX()+ "\n");

        for (ArrayList<GameObject>[] o: level) {
            for (ArrayList<GameObject> k: o) {
                for (GameObject i: k) {
                    if (i.getMaterial().name().equals("Floor") || i.getMaterial().name().equals("Cursor")) {

                    } else {
                        levelArr.add(i.getMaterial().name() + " " + y + " " + x + "\n");
                    }
                }
                x++;
            }
            y++;
            x = 0;
        }

        return levelArr;

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

            ArrayList<GameObject> arrayList = level[y][x];

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


}
