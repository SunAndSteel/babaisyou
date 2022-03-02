package com.baba.babaisyou.views;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        double tileSize = 48;

        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);

        Image icon = new Image(String.valueOf(Main.class.getResource("logo.png")));
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Baba is you");

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.F1) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });

        //Groupe contenant la grille
        Group map = new Group();

        //Génération de la grille
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 10; col++) {

                ImageView iv = new ImageView(String.valueOf(Main.class.getResource("wall.png")));

                iv.setFitHeight(tileSize);
                iv.setFitWidth(tileSize);
                iv.setX(col * tileSize);
                iv.setY(row * tileSize);

                map.getChildren().add(iv);
            }

        }

        //Ajouter le groupe au node de base root
        root.getChildren().add(map);
        //Groupe contenant la grille
        Group map2 = new Group();

        //Génération de la grille
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 10; col++) {

                ImageView iv = new ImageView(String.valueOf(Main.class.getResource("whiteDot.png")));

                iv.setFitHeight(tileSize);
                iv.setFitWidth(tileSize);
                iv.setX(col * tileSize);
                iv.setY(row * tileSize);

                map2.getChildren().add(iv);
            }

        }

        //Ajouter le groupe au node de base root
        root.getChildren().add(map2);



        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("Appuyez sur F1 pour quitter le mode plein écran");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
