package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Rule;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effects;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.view.View;
import com.baba.babaisyou.view.mapView;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {
        Grid gridInstance = Grid.getInstance();

        mapView.printMap();

//        System.out.println(Object.instances.get(Material.Floor));
//
//        ArrayList<Object> instances = new ArrayList<Object>(Object.instances.get(Material.Floor));
//
//        for (Object object : instances) {
//            object.setMaterial(Material.Wall);
//            mapView.printMap();
//            System.out.println("\n");
//        }

        Rule.checkRules();

        boolean main = true;
        Scanner myScanner = new Scanner(System.in);

        while (main) {
            String input = myScanner.nextLine();
            switch (input) {
                case "z" -> gridInstance.movePlayers(Direction.UP);
                case "s" -> gridInstance.movePlayers(Direction.DOWN);
                case "q" -> gridInstance.movePlayers(Direction.LEFT);
                case "d" -> gridInstance.movePlayers(Direction.RIGHT);
                case "quit" -> main = false;
            }
            gridInstance.checkWin();
            Rule.checkRules();
            mapView.printMap();
        }
        myScanner.close();

//        launch(View.class ,(String) null);

        ArrayOfObject[][] grid = Grid.getInstance().grid;

        for (ArrayOfObject[] row : grid) {
            for (ArrayOfObject arrayOfObject : row) {
//                for (Object object : arrayOfObject) {
//                    graphicsContext.drawImage(new Image(object.getMaterial().getImageUrl()), object.getX() * 32, object.getY() * 32);
//
//                }

                Object object = arrayOfObject.get(arrayOfObject.size() - 1);
                System.out.println(object.getX() + "   " + object.getY());
            }
        }
    }

}