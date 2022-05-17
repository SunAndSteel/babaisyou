package com.baba.babaisyou.view;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
//import com.baba.babaisyou.presenter.Game;
import com.baba.babaisyou.presenter.Menu;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Classe qui gère l'interface graphique du jeu
 */
public class LevelView {

    private static Stage stage;
    private static MapView map;
    private static int tileSize, tileHeight, tileWidth;
    private static Level level;
//    private static boolean menuBtnState;

//    private static final ArrayList<TranslateTransition> transitions = new ArrayList<>();


    /**
     * Vue d'un niveau
     * @param stage Le stage dans lequel on affiche la scène
     */
    public static void show(Stage stage, String levelName) {

        LevelView.stage = stage;
        HBox root = new HBox();
        Scene scene = new Scene(root, MainView.width, MainView.height);
        stage.setScene(scene);

        map = new MapView();

        try {
            map.setLevel(new Level(levelName));

        } catch (FileNotInCorrectFormat | IOException e) {
            // TODO
        }

        level = map.getLevel();

        stage.setTitle("BabaIsYou");
        scene.setFill(Color.rgb(21, 24, 31));
        scene.getStylesheets().add((new File("src/level.css")).toURI().toString());

        root.setAlignment(Pos.CENTER);
        root.getChildren().add(map);
        map.setAlignment(Pos.CENTER);


        //-------------------------------------------
        Button menuBtn = new Button();
        Image btnImg = new Image("file:src/main/resources/com/baba/babaisyou/views/menu1.png", 20 ,20 ,true, true);
        menuBtn.setGraphic(new ImageView(btnImg));
        menuBtn.setOpacity(0.2);

        VBox menu = new VBox();
        Button resumeBtn = new Button("Reprendre");
        Button homeBtn = new Button("Accueil");
        Button quitBtn = new Button("Quitter");
        menu.getChildren().addAll(resumeBtn, homeBtn, quitBtn);

        menu.setVisible(false);

        menuBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleMenu(menu, menuBtn);
            }
        }));

        resumeBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleMenu(menu, menuBtn);
            }
        }));

        quitBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        }));

        homeBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Menu.start(stage);
            }
        }));

        //-------------------------------------------

        root.getChildren().add(menuBtn);
        menuBtn.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().add(menu);
        menu.setAlignment(Pos.CENTER);

        HBox.setMargin(menu, new Insets(0, - 120, 0, 0));


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (map.getTransitions().isEmpty()) {
                    KeyCode code = event.getCode();

                    switch (code) {
                        case Z:
                            Mouvement.movePlayers(Direction.UP, level);
                            break;
                        case S:
                            Mouvement.movePlayers(Direction.DOWN, level);
                            break;
                        case Q:
                            Mouvement.movePlayers(Direction.LEFT, level);
                            break;
                        case D:
                            Mouvement.movePlayers(Direction.RIGHT, level);
                            break;
                        case ESCAPE:
                            toggleMenu(menu, menuBtn);
                            break;
                        case R:
                            try {
                                map.setLevel(new Level(levelName));
                            } catch (IOException | FileNotInCorrectFormat e) {
                                // TODO
                            }
                            level = map.getLevel();
                            break;

                        case F11:
                            stage.setFullScreen(!stage.isFullScreen());
                            break;
                        case BACK_SPACE:
                            Mouvement.reverse(level);
                            break;
                    }


                    if (level.isWin()) {
                        try {
                            map.setLevel(new Level(Level.getCurrentLevelNbr() + 1));
                        } catch (IOException | FileNotInCorrectFormat e) {
                            // TODO
                        }
                        level = map.getLevel();
                    }

                    Rule.checkRules(level);
                    map.drawMovedObjects();
                }
            }
        });

        WidthHeightListener();
        map.drawMovedObjects();
        stage.show();
    }

    public static int getTileSize() { return tileSize; }

    private static void WidthHeightListener() {

        tileHeight = ((int) stage.getHeight() - 50) / level.getSizeY();
        tileWidth = ((int) stage.getWidth()- 50) / level.getSizeX();

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
                tileWidth = (newVal.intValue() - 50) / level.getSizeX();
                resizeIVs();
            }
        });
    }

    private static void resizeIVs() {
        tileSize = Math.min(tileWidth, tileHeight);

        for (ArrayList<GameObject> objects : level) {
            for (GameObject object : objects) {
//                objectImageView.get(object).setFitHeight(tileSize);
                object.getIv().setFitHeight(tileSize);
            }
        }
    }

    private static void toggleMenu(VBox menu, Button menuBtn)  {

        boolean state = false;

        if (menu.isVisible()) {
            state = true;
            map.setEffect(null);

        } else {
            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.setRadius(10);
            map.setEffect(gaussianBlur);
        }

        menu.setVisible(!state);
        menuBtn.setVisible(state);
    }
}