package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.Floor;
import com.baba.babaisyou.model.Object;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Map {
    public static Object[][] map;

    public static void loadLevel(int levelNbr) {
        String[] size;
        try {
            // Crée le reader pour le level
            BufferedReader br = new BufferedReader(
                    new FileReader("src/main/resources/com/baba/babaisyou/levels/level" + levelNbr + ".txt"));


            String line;
            if ((line = br.readLine()) != null) {
                    size = line.split(" ");
                    map = new Object[Integer.parseInt(size[0])][Integer.parseInt(size[1])];

                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map[0].length; j++) {
                            map[i][j] = new Floor(i, j);
                        }
                    }

                    while ((line = br.readLine()) != null) {
                        String[] nextObject = line.split(" ");
                        String objectName = nextObject[0];
                        int x = Integer.parseInt(nextObject[1]);
                        int y = Integer.parseInt(nextObject[2]);
                        map[x][y] = (Object) Class.forName("com.baba.babaisyou.model." + objectName).getDeclaredConstructor(Integer.TYPE, Integer.TYPE).newInstance(x, y);

                    }

                    for (Object[] row : map) {
                        for (Object object : row) {
                            System.out.print(object);
                        }
                        System.out.println();
                    }

            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("File not in the correct format.");
        }
    }
}