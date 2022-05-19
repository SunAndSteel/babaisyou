package com.baba.babaisyou.view;

import com.baba.babaisyou.model.Level;
import com.baba.babaisyou.model.LevelLoader;
import com.baba.babaisyou.model.Mouvement;
import com.baba.babaisyou.model.Rule;
import com.baba.babaisyou.model.enums.Direction;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;


/**
 * Classe qui représente la vue d'un niveau
 */
public class LevelView {

    private static Stage stage;
    private static MapView map;
    private static Level level;
    private static Scene scene;
    private static String levelName;

    /**
     * Afficher un niveau
     * @param stage Le stage dans lequel on affiche la scène
     */
    public static void show(Stage stage, String levelName) {

        LevelView.levelName = levelName;
        LevelView.stage = stage;

        if (Objects.equals(levelName, "currentLevel")) {
            getCurrentLevelName();
        }

        BorderPane root = new BorderPane();
        StackPane center = new StackPane();

        scene = MainView.getScene();
        scene.getStylesheets().add((new File("src/level.css")).toURI().toString());
        scene.setRoot(root);

        map = new MapView();
        map.setAlignment(Pos.CENTER);

        center.getChildren().add(map);
        root.setCenter(center);

        map.setLevel(levelName);
        level = map.getLevel();

        Button menuBtn = new Button();
        Image btnImg = new Image("file:src/main/resources/com/baba/babaisyou/views/menuBtn.png", 20 ,20 ,true, true);
        menuBtn.setGraphic(new ImageView(btnImg));
        menuBtn.setOpacity(0.2);

        VBox menu = new VBox();
        Button resumeBtn = new Button("Reprendre");
        Button homeBtn = new Button("Sauvegarder et revenir à l'accueil");
        Button quitBtn = new Button("Sauvegarder et quitter");
        menu.getChildren().addAll(resumeBtn, homeBtn, quitBtn);

        menu.setVisible(false);

        //Gestionnaire des événements sur les boutons
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
                saveCurrentLevel();
                stage.close();
            }
        }));
        homeBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveCurrentLevel();
                MainView.show();
            }
        }));

        menuBtn.setAlignment(Pos.TOP_RIGHT);
        root.setRight(menuBtn);

        // Permet d'ajouter un espace vide dans le BorderPane à gauche, pour garder le centre bien au milieu.
        Region spacer = new Region();
        spacer.minWidthProperty().bind(menuBtn.widthProperty());
        root.setLeft(spacer);


        menu.setAlignment(Pos.CENTER);
        center.getChildren().add(menu);;

        loadControls(menu, menuBtn);

        map.WidthHeightListener(stage, false);
        map.resizeIVs();
        map.drawMovedObjects();
    }



    private static void toggleMenu(VBox menu, Button menuBtn)  {

        boolean state = false;

        if (menu.isVisible()) {
            state = true;
            map.setEffect(null);

        } else {

            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.setRadius(10);

            ColorAdjust colorAdjust = new ColorAdjust(0, 0, -0.6, 0);

            Blend effect = new Blend(BlendMode.DARKEN, colorAdjust, gaussianBlur);

            map.setEffect(effect);
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
                            map.calculateTileSize(stage, false);
                            map.resizeIVs();
                        } else {
                            LevelLoader.getLevelNameList().remove(nextLevelName);
                        }
                    }

                    Rule.checkRules(level);

                    if (level.isLoose()) {
                        map.setLevel(levelName);
                        level = map.getLevel();
                    }
                    map.drawMovedObjects();
                }
            }
        });

    }

    /**
     * Sauvegarde le nom du niveau courant dans le fichier
     */
    private static void saveCurrentLevel() {
        try {
            LevelLoader.save(level, "currentLevel");

            File file = new File("src/main/resources/com/baba/babaisyou/currentLevelName.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write(levelName);
            bw.close();

        } catch (IOException ignored) {

        }
    }

    /**
     * Sauvegarde le nom du level courant dans la variable d'instance "levelName"
     */
    private static void getCurrentLevelName() {
        try {
            File file = new File("src/main/resources/com/baba/babaisyou/currentLevelName.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            levelName = br.readLine();
            br.close();
        } catch (IOException ignored) {}
    }
}