package com.baba.babaisyou.view;

import com.baba.babaisyou.model.FileNotInCorrectFormat;
import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.Mouvement;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MapView extends GridPane {

    private final ArrayList<TranslateTransition> transitions = new ArrayList<>();
    private Level level;

//    public MapView(Level level) {
//        super();
//        this.level = level;
//    }

    public void setLevel(Level level) {
        this.level = level;
        getChildren().clear();
    }

    public Level getLevel() {
        return level;
    }

    public void drawMovedObjects() {
        Map<GameObject, Direction> movedObjects = Mouvement.getMovedObjects();
//        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

//        Map<GameObject, GameObjectView> objectImageView = GameObjectView.getObjectImageView();

//        Level level = Game.getInstance().getLevel();

//        if (level.isNewLevel()) {
//            level.setIsNewLevel(false);
//            getChildren().clear();
//        }

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

            transition.setByX(LevelView.getTileSize() * direction.dX);
            transition.setByY(LevelView.getTileSize() * direction.dY);

            transition.setNode(iv);

            transition.play();

            transition.setOnFinished( evt -> {
                getChildren().remove(iv);
                iv.setTranslateX(0);
                iv.setTranslateY(0);

                transitions.remove(transition);

                // Permet de vérifier si l'objet est toujours là (pour le changement de niveau)
                if (level.get(object.getX(), object.getY()).contains(object)) { // A changer
                    add(iv, object.getX(), object.getY());
                }
            });
        }
//        GameObject.resetMovedObjects();
        Mouvement.getMovedObjects().clear();
    }

    public ArrayList<TranslateTransition> getTransitions() {
        return transitions;
    }

//    public static MapView mapLoadLevel(String name) {
//
//        Level level;
//
//        try {
//            level = new Level(name);
//        } catch (FileNotInCorrectFormat | IOException e) {
//            return null;
//        }
//
//        return new MapView(level);
//    }
//
//    public static MapView mapLoadLevel(int levelNbr) {
//
//        Level level;
//
//        try {
//            level = new Level(levelNbr);
//        } catch (FileNotInCorrectFormat | IOException e) {
//            return null;
//        }
//
//        return new MapView(level);
//    }
}
