package com.baba.babaisyou.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuView {

    public static void show(Stage primaryStage) {
        StackPane root = new StackPane();

        Button play = new Button("Play");
        Button builder = new Button("Builder");

        VBox vb = new VBox(play, builder);
        vb.setAlignment(Pos.CENTER);

        root.getChildren().add(vb);

        Scene scene = new Scene(root, MainView.width, MainView.height);

        primaryStage.setScene(scene);

        play.setOnMouseClicked((MouseEvent event) -> {
            Menu.playButtonAction(primaryStage);
        });
        builder.setOnMouseClicked((MouseEvent event) -> {
            Menu.builderButtonAction(primaryStage);
        });

    }


}
