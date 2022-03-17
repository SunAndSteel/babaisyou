package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Material;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Level {
    private static int sizeX, sizeY;
    private static ArrayOfObject[][] level;
    private static int currentLevelNbr;


    /**
     * Créer un level à partir d'un fichier texte
     * @param levelNbr Le numéro du level à charger
     * @return Une liste en 2 dimensions d'objets représentant la map
     */
    public static ArrayOfObject[][] loadlevel(int levelNbr) {

        currentLevelNbr = levelNbr;
        return loadlevel("level" + levelNbr);
    }


    public static ArrayOfObject[][] loadlevel(String name) {

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
                level = new ArrayOfObject[sizeY][sizeX];
                for (int row = 0; row < level.length; row++) {
                    for (int col = 0; col < level[row].length; col++) {
                        level[row][col] = new ArrayOfObject();
                    }
                }

                //Ajoute des objets des floors sur toutes la map
                for (int i = 0; i < level.length; i++) {
                    for (int j = 0; j < level[0].length; j++) {
                        level[i][j].add(new Object(Material.Floor, j, i));
                    }
                }

                //Récupère les objets dans chaque ligne et les ajoutes a la liste en 2 dimensions
                while ((line = br.readLine()) != null) {
                    String[] nextObject = line.split(" ");
                    String objectName = nextObject[0];
                    int x = Integer.parseInt(nextObject[1]);
                    int y = Integer.parseInt(nextObject[2]);
                    level[y][x].add(new Object(objectName, x, y));
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
        }
        return level;
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
    public static int getSizeX() {
        return sizeX;
    }

    /**
     * @return La taille Y du level
     */
    public static int getSizeY() {
        return sizeY;
    }
}
