package com.baba.babaisyou.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class MainView extends Application {

    public static int width;
    public static int height;
    public static Scene scene;
    private static StackPane root;

    public static void show(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new StackPane();
        Button resume = new Button("Reprendre");
        Button play = new Button("Commencer");
        Button builder = new Button("Constructeur de niveau");
        Button quitBtn = new Button("Quitter");
        Label title = new Label("Baba is you");

        File currentLevel = new File("src/main/resources/com/baba/babaisyou/levels/currentLevel.txt");

        VBox vb;
        if (currentLevel.exists()) {
            vb = new VBox(title, resume, play, builder, quitBtn);
            play.setText("Niveaux");
        } else {
            vb = new VBox(title, play, builder, quitBtn);
        }

        vb.setAlignment(Pos.CENTER);

        root.getChildren().add(vb);

        //Fenêtre deux fois moins grande que l'écran
        width = (int) Screen.getPrimary().getBounds().getWidth()/2;
        height = (int) Screen.getPrimary().getBounds().getHeight()/2;

        scene = new Scene(root, MainView.width, MainView.height);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());
        scene.setFill(Color.rgb(21, 24, 31));

        primaryStage.setScene(scene);

        play.setOnMouseClicked(event -> {
            if(Objects.equals(play.getText(), "Niveaux")) {
                LevelView.show(primaryStage, "level1");
            } else if (Objects.equals(play.getText(), "Commencer")) {
                LevelView.show(primaryStage, "level1");
//                try {
//                    currentLevel.createNewFile();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });
        builder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelBuilderView.show(primaryStage);
            }
        });
        quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
            }
        });

        primaryStage.setTitle("BabaIsYou");

        primaryStage.getIcons().add(new Image("file:src/main/resources/com/baba/babaisyou/views/Baba.png"));

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    public static void show() {

        scene.setRoot(root);

    }

    public static Scene getScene() {
        return scene;
    }
}
