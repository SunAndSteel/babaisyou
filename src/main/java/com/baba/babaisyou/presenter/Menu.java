package com.baba.babaisyou.presenter;

import com.baba.babaisyou.view.LevelView;
import com.baba.babaisyou.view.MenuView;
import com.baba.babaisyou.view.Selection;
import javafx.stage.Stage;

public class Menu {
    public static void start(Stage primaryStage) {
        MenuView.show(primaryStage);
    }

    public static void playButtonAction(Stage primaryStage) {
        Selection.show(primaryStage);
    }

    public static void builderButtonAction(Stage primaryStage) {
        LevelBuilder.start(primaryStage);
    }

    public static void playButtonActionStart(Stage primaryStage){
        LevelView.show(primaryStage, "level1");
    }
}
