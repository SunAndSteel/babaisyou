package com.baba.babaisyou.view;

import com.baba.babaisyou.model.*;
import com.baba.babaisyou.model.enums.Direction;
//import com.baba.babaisyou.presenter.Game;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


/**
 * Classe qui gère l'interface graphique du jeu
 */
public class LevelView {

    private static Stage stage;
    private static MapView map;
    private static int tileSize, tileHeight, tileWidth;
    private static Level level;
    private static Scene scene;
    private static String levelName;

//    private static boolean menuBtnState;

//    private static final ArrayList<TranslateTransition> transitions = new ArrayList<>();


    /**
     * Vue d'un niveau
     * @param stage Le stage dans lequel on affiche la scène
     */
    public static void show(Stage stage, String levelName) {

        LevelView.levelName = levelName;
        LevelView.stage = stage;

        HBox root = new HBox();

        scene = MainView.getScene();

        scene.setRoot(root);

        map = new MapView();

        map.setLevel(levelName);
        level = map.getLevel();

        scene.getStylesheets().add((new File("src/level.css")).toURI().toString());

        root.setAlignment(Pos.CENTER);
        root.getChildren().add(map);
        map.setAlignment(Pos.CENTER);


        Button menuBtn = new Button();
        Image btnImg = new Image("file:src/main/resources/com/baba/babaisyou/views/menuBtn.png", 20 ,20 ,true, true);
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
                MainView.show();
            }
        }));

        root.getChildren().add(menuBtn);
        menuBtn.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().add(menu);
        menu.setAlignment(Pos.CENTER);

        HBox.setMargin(menu, new Insets(0, - 120, 0, 0));

        loadControls(menu, menuBtn);

        map.WidthHeightListener(stage);
        map.resizeIVs();
        map.drawMovedObjects();
        stage.show();
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

    private static void loadControls(VBox menu, Button menuBtn) {

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
                            map.setLevel(levelName);
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
                        String nextLevelName = LevelLoader.nextLevelName(levelName);

                        if (map.setLevel(nextLevelName)) {
                            level = map.getLevel();
                            levelName = nextLevelName;
                        }
                    }

                    Rule.checkRules(level);
                    map.drawMovedObjects();
                }
            }
        });

    }
}