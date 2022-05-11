package com.baba.babaisyou.presenter;
import com.baba.babaisyou.presenter.LevelBuilder;
import com.baba.babaisyou.view.LevelView;
import com.baba.babaisyou.view.MenuView;
import javafx.stage.Stage;

public class Menu {
    public static void start(Stage primaryStage) {
        MenuView.show(primaryStage);
    }

    public static void playButtonAction(Stage primaryStage) {
        LevelView.show(primaryStage);
    }

    public static void builderButtonAction(Stage primaryStage) {
        LevelBuilder.start(primaryStage);
    }

}
