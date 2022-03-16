package com.baba.babaisyou.view;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu {
    public static void start(Stage primaryStage) {

        GridPane root = new GridPane();
        Scene menu = new Scene(root, MainView.width, MainView.height, Color.BLACK);
        Button btnPlay = new Button();

        btnPlay.setText("Play");

        root.setAlignment(Pos.CENTER);

        root.add(btnPlay, 1, 1);

        primaryStage.setScene(menu);

        btnPlay.setOnAction((ActionEvent handler) -> {
            View.show(primaryStage);
        } );
    }
}
