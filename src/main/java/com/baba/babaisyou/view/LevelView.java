package com.baba.babaisyou.view;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Game;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
     * Vue d'un niveau
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
        scene.setFill(Color.rgb(21, 24, 31));

        root.setAlignment(Pos.CENTER);

        scene.setOnKeyPressed( (KeyEvent event) -> {

            if (transitions.isEmpty()) {
                KeyCode code = event.getCode();

                switch (code) {
                    case Z -> Mouvement.movePlayers(Direction.UP, level);
                    case S -> Mouvement.movePlayers(Direction.DOWN, level);
                    case Q -> Mouvement.movePlayers(Direction.LEFT, level);
                    case D -> Mouvement.movePlayers(Direction.RIGHT, level);
                    case ESCAPE -> stage.close(); // on devrait le mettre dans MainView
                    case R -> {
                        game.mapLoadLevel(Level.getCurrentLevelNbr());
                        level = game.getLevel();
                    }
                    case F11 -> stage.setFullScreen(!stage.isFullScreen());
                    case BACK_SPACE -> Mouvement.reverse(level);
                }

                game.update();
                level = game.getLevel();
                drawMovedObjects(root);
            }
        });

        WidthHeightListener();
        drawMovedObjects(root);
        stage.show();
    }

    /**
     * @return Le moment du début de l'exécution
     */
    public static GridPane getRoot() {
        return root;
    }

    public static int getTileSize() { return tileSize; }

    private static void WidthHeightListener() {

        tileHeight = ((int) stage.getHeight() - 50) / level.getSizeY();
        tileWidth = ((int) stage.getWidth()- 50) / level.getSizeX();

        tileSize = Math.min(tileWidth, tileHeight);

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
        Map<GameObject, GameObjectView> objectImageView = GameObjectView.getObjectImageView();

        for (ArrayList<GameObject> objects : level) {
            for (GameObject object : objects) {
//                objectImageView.get(object).setFitHeight(tileSize);
                object.getIv().setFitHeight(tileSize);
            }
        }
    }

    private static int cnt = 0;

    public static void drawMovedObjects(GridPane gridPane) {
        Map<GameObject, Direction> movedObjects = Mouvement.getMovedObjects();
//        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

//        Map<GameObject, GameObjectView> objectImageView = GameObjectView.getObjectImageView();

        Level level = Game.getInstance().getLevel();

        if (level.isNewLevel()) {
            level.setIsNewLevel(false);
            gridPane.getChildren().clear();
        }

        for (GameObject object : movedObjects.keySet()) {

            ImageView iv = object.getIv();


//            if (objectImageView.containsKey(object)) {
//                iv = objectImageView.get(object);
//
//            } else {
//                iv = new GameObjectView(object);
//            }

            Direction direction = movedObjects.get(object);

            if (direction == Direction.NONE) {

                gridPane.getChildren().remove(iv);

                // Permet de vérifier si l'objet est toujours là (pour le changement de niveau)
                if (Game.getInstance().getLevel().get(object.getX(), object.getY()).contains(object)) {
                    gridPane.add(iv, object.getX(), object.getY());
                }

                continue;
            }

            Material material = object.getMaterial();

            if (material.isDirectional()) {

                Image image;

                if (object.getReverse()) {
                     image = material.getFrames()[direction.reverseDirection().index];
                     object.setReverse(false);
                } else {
                    image = material.getFrames()[direction.index];
                }
                iv.setImage(image);

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
//        GameObject.resetMovedObjects();
        Mouvement.getMovedObjects().clear();
    }
}