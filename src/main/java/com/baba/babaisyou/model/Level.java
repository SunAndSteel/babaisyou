package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Level {
    private static int sizeX, sizeY;
    private static ArrayOfObject[][] level;
    private static int currentLevelNbr;

    public static ArrayOfObject[][] loadlevel(int levelNbr) {

        currentLevelNbr = levelNbr;

        String[] size;
        try {
            // Crée le reader pour le level
            BufferedReader br = new BufferedReader(
                    new FileReader("src/main/resources/com/baba/babaisyou/levels/level" + levelNbr + ".txt"));


            String line;
            if ((line = br.readLine()) != null) {
                size = line.split(" ");
                sizeX = Integer.parseInt(size[0]);
                sizeY = Integer.parseInt(size[1]);
//                    map = new Object[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
                level = new ArrayOfObject[sizeY][sizeX]; // on peut regler l'erreur en creant une class ArrayOfTile ...
                for (int i = 0; i < level.length; i++) {
                    for (int j = 0; j < level[i].length; j++) {
                        level[i][j] = new ArrayOfObject();
                    }
                }


                for (int i = 0; i < level.length; i++) {
                    for (int j = 0; j < level[0].length; j++) {
//                            map[i][j] = new Floor(i, j);
//                        map[i][j] = new Tile(Material.Floor, i, j);
                        level[i][j].add(new Object(Material.Floor, j, i));
                    }
                }

                while ((line = br.readLine()) != null) {
                    String[] nextObject = line.split(" ");
                    String objectName = nextObject[0];
                    int x = Integer.parseInt(nextObject[1]);
                    int y = Integer.parseInt(nextObject[2]);
//                        map[y][x] = (Object) Class.forName("com.baba.babaisyou.model." + objectName).getDeclaredConstructor(Integer.TYPE, Integer.TYPE).newInstance(x, y);
                    level[y][x].add(new Object(objectName, x, y));
                }

            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException /*| ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException*/ e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("File not in the correct format.");
        }

        for (ArrayOfObject[] row : level) {
            for (ArrayOfObject arrayOfObject : row) {
//                for (Object object : arrayOfObject) {
//                    graphicsContext.drawImage(new Image(object.getMaterial().getImageUrl()), object.getX() * 32, object.getY() * 32);
//
//                }

                Object object = arrayOfObject.get(arrayOfObject.size() - 1);
                System.out.println(object.getX() + "   " + object.getY());
            }
        }





        return level;
    }

    public static int getCurrentLevelNbr() {
        return currentLevelNbr;
    }

    public static int getSizeX() {
        return sizeX;
    }

    public static int getSizeY() {
        return sizeY;
    }
}
