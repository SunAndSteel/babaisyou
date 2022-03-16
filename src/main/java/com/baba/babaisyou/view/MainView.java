package com.baba.babaisyou.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.spi.ToolProvider;


public class MainView extends Application {

    public static int width;
    public static int height;

    public static void show(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("baba is you");

        primaryStage.getIcons().add(new Image("file:src/main/resources/com/baba/babaisyou/views/Baba.png"));

        Menu.start(primaryStage);

        //Fenêtre deux fois moins grande que l'écran
        width = (int) Screen.getPrimary().getBounds().getWidth()/2;
        height = (int) Screen.getPrimary().getBounds().getWidth()/2;



        primaryStage.show();
    }
}