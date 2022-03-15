package com.baba.babaisyou.view;

import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Rule;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.presenter.Grid;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Classe qui gère l'interface graphique du jeu
 */
public class View extends Application {

    private static long startTime;
    private static GraphicsContext graphicsContext;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX
     * @param primaryStage Le stage dans lequel on affiche la scène
     * @throws Exception Gérer les exceptions si l'exécution se passe mal
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Grid gridInstance = Grid.getInstance();
        Rule.checkRules();
        primaryStage.setTitle("BabaIsYou");

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(Level.getSizeX() * 32, Level.getSizeY() * 32);
        root.getChildren().add(canvas);

        graphicsContext = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<String>();

        scene.setOnKeyPressed( (KeyEvent event) -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.Z) {
                gridInstance.movePlayers(Direction.UP);
            } else if (code == KeyCode.S) {
                gridInstance.movePlayers(Direction.DOWN);
            } else if (code == KeyCode.D) {
                gridInstance.movePlayers(Direction.RIGHT);
            } else if (code == KeyCode.Q) {
                gridInstance.movePlayers(Direction.LEFT);
            } else if (code == KeyCode.ESCAPE) {
                primaryStage.close();
            }
            gridInstance.checkWin();
            Rule.checkRules();
        });

//        startTime = System.nanoTime();
        new Timer().start();

        primaryStage.show();
    }

    /**
     * @return Le moment du début de l'exécution
     */
    public static long getStartTime() {
        return startTime;
    }

    /**
     * @return Le contexte graphique
     */
    public static GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
}