package com.baba.babaisyou.view;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class View extends Application {

    private static long startTime;
    private static GraphicsContext graphicsContext;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Grid.getInstance();
        primaryStage.setTitle("BabaIsYou");

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(Level.getSizeX() * 32, Level.getSizeY() * 32);
        root.getChildren().add(canvas);

        graphicsContext = canvas.getGraphicsContext2D();


        startTime = System.nanoTime();
        new Timer().start();


        primaryStage.show();
    }

    public static long getStartTime() {
        return startTime;
    }

    public static GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
}