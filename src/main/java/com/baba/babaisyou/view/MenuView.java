package com.baba.babaisyou.view;

import com.baba.babaisyou.presenter.Menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class MenuView {

    private static VBox vb;

    public static void show(Stage primaryStage) {
        StackPane root = new StackPane();
        Button resume = new Button("Reprendre");
        Button play = new Button("Commencer");
        Button builder = new Button("Constructeur de niveau");
        Label title = new Label("Baba is you");

        File currentLevel = new File("src/main/resources/com/baba/babaisyou/levels/currentLevel.txt");

        if (currentLevel.exists()) {
            vb = new VBox(title, resume, play, builder);
            play.setText("Niveaux");
        } else {
            vb = new VBox(title, play, builder);
        }

        vb.setAlignment(Pos.CENTER);

        root.getChildren().add(vb);

        Scene scene = new Scene(root, MainView.width, MainView.height);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());

        primaryStage.setScene(scene);

        play.setOnMouseClicked((MouseEvent event) -> {
            if(Objects.equals(play.getText(), "Niveaux")) {
                Menu.playButtonAction(primaryStage);
            } else if (Objects.equals(play.getText(), "Commencer")) {
                Menu.playButtonActionStart(primaryStage);
                try {
                    currentLevel.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        builder.setOnMouseClicked((MouseEvent event) -> {
            Menu.builderButtonAction(primaryStage);
        });

    }


}
