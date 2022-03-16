package com.baba.babaisyou.view;

import com.baba.babaisyou.model.Rule;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.presenter.Grid;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Classe qui gère l'interface graphique du jeu
 */
public class View {

    private static long startTime;
    private static GridPane root;

    /**
     * JavaFX
     * @param primaryStage Le stage dans lequel on affiche la scène
     */
    public static void show(Stage primaryStage) {

        Grid gridInstance = Grid.getInstance();
        Rule.checkRules();
        primaryStage.setTitle("BabaIsYou");

        root = new GridPane();
        Scene scene = new Scene(root, MainView.width, MainView.height);
        primaryStage.setScene(scene);

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

        root.setAlignment(Pos.CENTER);

        new Timer().start();

        primaryStage.show();
    }

    /**
     * @return Le moment du début de l'exécution
     */
    public static long getStartTime() {
        return startTime;
    }

    public static GridPane getGridPane() {
        return root;
    }
}