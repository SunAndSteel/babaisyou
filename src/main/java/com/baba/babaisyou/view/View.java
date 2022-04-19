package com.baba.babaisyou.view;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.presenter.Grid;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Classe qui gère l'interface graphique du jeu
 */
public class View {

    private static GridPane root;
    private static Stage primaryStage;
    private static int tileHeight, tileWight;

    /**
     * JavaFX
     * @param primaryStage Le stage dans lequel on affiche la scène
     */
    public static void show(Stage primaryStage) {

        View.primaryStage = primaryStage;
        Grid gridInstance = Grid.getInstance();
        primaryStage.setTitle("BabaIsYou");
        Rule.checkRules();

        root = new GridPane();
        Scene scene = new Scene(root, MainView.width, MainView.height);
        primaryStage.setScene(scene);

        root.setAlignment(Pos.CENTER);

        scene.setOnKeyPressed( (KeyEvent event) -> {
            KeyCode code = event.getCode();

            switch (code) {
                case Z -> gridInstance.movePlayers(Direction.UP);
                case S -> gridInstance.movePlayers(Direction.DOWN);
                case Q -> gridInstance.movePlayers(Direction.LEFT);
                case D -> gridInstance.movePlayers(Direction.RIGHT);
                case ESCAPE -> primaryStage.close(); // on devrait le mettre dans MainView
                case R -> gridInstance.mapLoadLevel(Level.getCurrentLevelNbr());
                case F11 -> primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
            gridInstance.checkWin();
            Rule.checkRules();
        });

        new Timer().start();

        primaryStage.heightProperty().addListener((observable, oldVal, newVal) -> {
            tileHeight = (newVal.intValue() - 50) / Level.getSizeY();
            root.getChildren().clear();
            drawAll();
        });

        primaryStage.widthProperty().addListener((observable, oldVal, newVal) -> {
            tileWight = (newVal.intValue() - 50) / Level.getSizeX();
            root.getChildren().clear();
            drawAll();
        });

        primaryStage.show();
    }

    /**
     * @return Le moment du début de l'exécution
     */
    public static GridPane getRoot() {
        return root;
    }

    public static Stage getPrimaryStage() { return primaryStage; }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static int getTileWight() {
        return tileWight;
    }

    public static void drawAll() {
        ArrayOfObject[][] grid = Grid.getInstance().grid;

        for (ArrayOfObject[] row : grid) {

            for (ArrayOfObject objects : row) {

                StackPane stackPane = new StackPane();

                for (Object object : objects) {
                    ImageView iv = new ImageView(object.getMaterial().getFrames()[0]);
                    iv.setPreserveRatio(true);
                    iv.setFitHeight(Math.min(View.getTileHeight(), View.getTileWight()));

                    stackPane.getChildren().add(iv);
                    object.iv = iv;
                }
                root.add(stackPane, objects.get(0).getX(), objects.get(0).getY());
            }
        }
    }
}