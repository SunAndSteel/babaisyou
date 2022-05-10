package com.baba.babaisyou.view;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Game;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;


/**
 * Classe qui gère l'interface graphique du jeu
 */
public class LevelView {

    private static Stage stage;
    private static GridPane root;
    private static int tileSize, tileHeight, tileWidth;
    private static Level level;
    private static final ArrayList<TranslateTransition> transitions = new ArrayList<>();


    /**
     * JavaFX
     * @param stage Le stage dans lequel on affiche la scène
     */
    public static void show(Stage stage) {

        LevelView.stage = stage;
        root = new GridPane();
        Scene scene = new Scene(root, MainView.width, MainView.height);
        stage.setScene(scene);

        Game game = Game.getInstance();
        game.mapLoadLevel(1);
        level = game.getLevel();
        stage.setTitle("BabaIsYou");
        Rule.checkAllRules(level);
        scene.setFill(Color.rgb(21, 24, 31));

        root.setAlignment(Pos.CENTER);

        scene.setOnKeyPressed( (KeyEvent event) -> {

            if (transitions.isEmpty()) {
                KeyCode code = event.getCode();

                switch (code) {
                    case Z -> game.movePlayers(Direction.UP);
                    case S -> game.movePlayers(Direction.DOWN);
                    case Q -> game.movePlayers(Direction.LEFT);
                    case D -> game.movePlayers(Direction.RIGHT);
                    case ESCAPE -> stage.close(); // on devrait le mettre dans MainView
                    case R -> {
                        game.mapLoadLevel(Level.getCurrentLevelNbr());
                        level = game.getLevel();
                        Rule.checkAllRules(level);
                    }
                    case F11 -> stage.setFullScreen(!stage.isFullScreen());
                    case BACK_SPACE -> game.reverse();
                }
                game.update();
                drawMovedObjects(root);
            }
        });

        WidthHeightListener();
        drawMovedObjects(root);
        stage.show();
    }

    public static GridPane getRoot() {
        return root;
    }

    public static int getTileSize() { return tileSize; }

    public static void WidthHeightListener() {
        stage.heightProperty().addListener((observable, oldVal, newVal) -> {
            tileHeight = (newVal.intValue() - 50) / level.getSizeY();
            resizeIVs();
        });

        stage.widthProperty().addListener((observable, oldVal, newVal) -> {
            tileWidth = (newVal.intValue() - 50) / level.getSizeX();
            resizeIVs();
        });
    }

    private static void resizeIVs() {
        tileSize = Math.min(tileWidth, tileHeight);

        for (ArrayList<GameObject> objects : level) {
            for (GameObject object : objects) {
                object.getIv().setFitHeight(tileSize);
            }
        }
    }

    public static void drawMovedObjects(GridPane gridPane) {
        Map<GameObject, Direction> movedObjects = GameObject.getMovedObjects();


        for (GameObject object : movedObjects.keySet()) {

            ImageView iv = object.getIv();

            Direction direction = movedObjects.get(object);

            if (direction == Direction.NONE) {

                gridPane.getChildren().remove(iv);
                gridPane.add(iv, object.getX(), object.getY());

                continue;
            }

            Material material = object.getMaterial();

            if (material.isDirectional() && !material.name().equals("Cursor")) {
                iv.setImage(material.getFrames()[direction.index]);

            }

            // Cela permet de mettre l'ImageView à la fin de la liste children, pour éviter des bugs visuels avec TranslateTransition
            gridPane.getChildren().remove(iv);
            gridPane.getChildren().add(iv);

            TranslateTransition transition = new TranslateTransition(Duration.millis(75));

            transitions.add(transition);

            transition.setByX(LevelView.getTileSize() * direction.dX);
            transition.setByY(LevelView.getTileSize() * direction.dY);

            transition.setNode(iv);

            transition.play();

            transition.setOnFinished( evt -> {
                gridPane.getChildren().remove(iv);
                iv.setTranslateX(0);
                iv.setTranslateY(0);

                transitions.remove(transition);

                // Permet de vérifier si l'objet est toujours là (pour le changement de niveau)
                if (Game.getInstance().getLevel().get(object.getX(), object.getY()).contains(object)) { // A changer
                    gridPane.add(iv, object.getX(), object.getY());
                }
            });
        }
        GameObject.resetMovedObjects();
    }
}