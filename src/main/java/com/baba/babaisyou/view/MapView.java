package com.baba.babaisyou.view;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Mouvement;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;

public class MapView extends GridPane {

    private final ArrayList<TranslateTransition> transitions = new ArrayList<>();
    private Level level;
    private static int tileSize, tileHeight, tileWidth;

    public boolean setLevel(String levelName) {

        Level newLevel = new Level(levelName);

        if (newLevel.getLevelGrid() != null) {
            getChildren().clear();
            level = newLevel;
            return true;
        }

        return false;
    }

    public void setLevel(String levelName, String path) {

        Level newLevel = new Level(levelName, path);

        if (newLevel.getLevelGrid() != null) {
            getChildren().clear();
            level = newLevel;
        }
    }


    public Level getLevel() {
        return level;
    }

    public void drawMovedObjects() {
        Map<GameObject, Direction> movedObjects = Mouvement.getMovedObjects();

        for (GameObject object : movedObjects.keySet()) {

            ImageView iv = object.getIv();

            Direction direction = movedObjects.get(object);

            if (direction == Direction.NONE) {

                getChildren().remove(iv);

                // Permet de vérifier si l'objet est toujours là (pour le changement de niveau)
                if (level.get(object.getX(), object.getY()).contains(object)) {
                    add(iv, object.getX(), object.getY());
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
            getChildren().remove(iv);
            getChildren().add(iv);

            TranslateTransition transition = new TranslateTransition(Duration.millis(75));

            transitions.add(transition);

            transition.setByX(MapView.getTileSize() * direction.dX);
            transition.setByY(MapView.getTileSize() * direction.dY);

            transition.setNode(iv);

            transition.play();

            transition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent evt) {
                    MapView.this.getChildren().remove(iv);
                    iv.setTranslateX(0);
                    iv.setTranslateY(0);

                    transitions.remove(transition);

                    // Permet de vérifier si l'objet est toujours là (pour le changement de niveau)
                    if (level.get(object.getX(), object.getY()).contains(object)) { // A changer
                        MapView.this.add(iv, object.getX(), object.getY());
                    }
                }
            });
        }
        Mouvement.getMovedObjects().clear();
    }

    public ArrayList<TranslateTransition> getTransitions() {
        return transitions;
    }

    public static int getTileSize() { return tileSize; }

    public void WidthHeightListener(Stage stage, boolean constructor) {

        calculateTileSize(stage, constructor);

        tileSize = Math.min(tileWidth, tileHeight);

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
                tileHeight = (newVal.intValue() - 50) / level.getSizeY();
                resizeIVs();
            }
        });

        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {

                if (!constructor) {
                    tileWidth = (newVal.intValue() - 50) / level.getSizeX();

                } else {
                    tileWidth = (newVal.intValue() - 500) / level.getSizeX();
                }

                resizeIVs();
            }
        });
    }

    public void resizeIVs() {
        tileSize = Math.min(tileWidth, tileHeight);

        for (ArrayList<GameObject> objects : level) {
            for (GameObject object : objects) {
                object.getIv().setFitHeight(tileSize);
            }
        }
    }

    public void calculateTileSize(Stage stage, boolean constructor) {
        tileHeight = ((int) stage.getHeight() - 50) / level.getSizeY();

        if (!constructor) {
            tileWidth = ((int) stage.getWidth() - 50) / level.getSizeX();

        } else {
            tileWidth = ((int) stage.getWidth() - 500) / level.getSizeX();

        }
    }
}
