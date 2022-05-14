package com.baba.babaisyou.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TestView {
    public static void show(Stage primaryStage) {

        GridPane root = new GridPane();

        Scene test = new Scene(root, 300,300);

        primaryStage.setScene(test);

    }

}
