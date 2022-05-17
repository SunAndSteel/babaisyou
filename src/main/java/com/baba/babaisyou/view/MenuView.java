package com.baba.babaisyou.view;

import com.baba.babaisyou.presenter.Menu;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MenuView {

    private static VBox vb;

    public static void show(Stage primaryStage) {
        StackPane root = new StackPane();
        Button resume = new Button("Reprendre");
        Button play = new Button("Commencer");
        Button builder = new Button("Constructeur de niveau");
        Button quitBtn = new Button("Quitter");
        Label title = new Label("Baba is you");

        File currentLevel = new File("src/main/resources/com/baba/babaisyou/levels/currentLevel.txt");

        if (currentLevel.exists()) {
            vb = new VBox(title, resume, play, builder, quitBtn);
            play.setText("Niveaux");
        } else {
            vb = new VBox(title, play, builder, quitBtn);
        }

        vb.setAlignment(Pos.CENTER);

        root.getChildren().add(vb);

        Scene scene = new Scene(root, MainView.width, MainView.height);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());

        primaryStage.setScene(scene);

        play.setOnMouseClicked(event -> {
            if(Objects.equals(play.getText(), "Niveaux")) {
                Menu.playButtonAction(primaryStage);
            } else if (Objects.equals(play.getText(), "Commencer")) {
                Menu.playButtonActionStart(primaryStage);
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
                Menu.builderButtonAction(primaryStage);
            }
        });
        quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
            }
        });

    }


}
