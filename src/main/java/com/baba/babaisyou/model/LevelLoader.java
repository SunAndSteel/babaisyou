package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Material;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class LevelLoader {

    private static final ArrayList<String> levelNameList = getLevelNames();

    /**
     * Créer une liste en 2 dimensions d'objets représentant la map à partir d'un fichier texte
     * @param name Le numéro du level à charger
     */
    public static void loadLevel(String name, Level level) throws IOException, FileNotInCorrectFormat {

        String[] size;
        // Crée le reader pour le level
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/com/baba/babaisyou/levels/" + name + ".txt"));

        String line = br.readLine();

        if (line == null) {
            throw new FileNotInCorrectFormat();
        }

        //Récupérer la taille de la map dans la première ligne
        size = line.split(" ");

        if (size.length != 2) {
            throw new FileNotInCorrectFormat();
        }

        int sizeX;
        int sizeY;

        try {
            sizeX = Integer.parseInt(size[0]);
            sizeY = Integer.parseInt(size[1]);

        } catch (NumberFormatException e) {
            throw new FileNotInCorrectFormat();
        }

        level.setSizeX(sizeX);
        level.setSizeY(sizeY);

        createEmptyGrid(sizeX, sizeY, level);
        ArrayList<GameObject>[][] levelGrid = level.getLevelGrid();

        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

        //Récupère les objets dans chaque ligne et les ajoutes à la liste en 2 dimensions
        while ((line = br.readLine()) != null) {

            String[] nextObject = line.split(" ");

            if (nextObject.length < 3) {
                throw new FileNotInCorrectFormat();
            }
            String objectName = nextObject[0];

            int x;
            int y;

            try {
                x = Integer.parseInt(nextObject[1]);
                y = Integer.parseInt(nextObject[2]);

            } catch (NumberFormatException e) {
                throw new FileNotInCorrectFormat();
            }

            Material material = Material.valueOf(objectName);
            GameObject object = new GameObject(material, x, y);
            levelGrid[y][x].add(object);
            instances.get(material).add(object);
        }
        level.setLevelGrid(levelGrid);
        br.close();
    }

    @SuppressWarnings("unchecked")
    public static void createEmptyGrid(int sizeX, int sizeY, Level level) {

        //Créé une liste en 2 dimensions de la taille de la map
        ArrayList<GameObject>[][] levelGrid = new ArrayList[sizeY][sizeX];
        for (int row = 0; row < sizeY; row++) {
            for (int col = 0; col < sizeX; col++) {
                levelGrid[row][col] = new ArrayList<>();
            }
        }

        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

        //Ajoute des objets des floors sur toutes la map
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                GameObject object = new GameObject(Material.Floor, j, i);
                levelGrid[i][j].add(object);
                instances.get(Material.Floor).add(object);

            }
        }

        level.setLevelGrid(levelGrid);
    }

    public static void save(Level level, String fileName) throws IOException {

        File file = new File("src/main/resources/com/baba/babaisyou/levels/" + fileName + ".txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        bw.write(level.getSizeX() + " " + level.getSizeY() + "\n");

        for (ArrayList<GameObject> objects : level) {
            for (GameObject object : objects) {

                Material material = object.getMaterial();

                if (material != Material.Cursor && material != Material.Floor) {

                    bw.write(material.name() + " " + object.getX() + " " + object.getY() + "\n");

                }
            }
        }
        bw.close();
    }


    /**
     * @return La liste des niveaux
     */
    public static ArrayList<String> getLevelNames() {
        ArrayList<String> levelsNames = new ArrayList<>();
        File f = new File("src/main/resources/com/baba/babaisyou/levels");

        for (File file : Objects.requireNonNull(f.listFiles())) {
            String filteredName = file.getName().substring(0, file.getName().length() - 4);
            if(!file.getName().substring(0, file.getName().length() - 4).equals("currentLevel")) {
                levelsNames.add(filteredName);
            }
        }

        return levelsNames;
    }

    public static String nextLevelName(String levelName) {

        int index = levelNameList.indexOf(levelName);

        return levelNameList.get(index % levelNameList.size());

    }

}
