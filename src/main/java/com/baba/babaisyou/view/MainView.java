package com.baba.babaisyou.view;

import com.baba.babaisyou.presenter.Menu;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainView extends Application {

    public static int width;
    public static int height;

    public static void show(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("BabaIsYou");

        primaryStage.getIcons().add(new Image("file:src/main/resources/com/baba/babaisyou/views/Baba.png"));

        //Fenêtre deux fois moins grande que l'écran
        width = (int) Screen.getPrimary().getBounds().getWidth()/2;
        height = (int) Screen.getPrimary().getBounds().getHeight()/2;

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        Menu.start(primaryStage);

        primaryStage.show();
    }
}
