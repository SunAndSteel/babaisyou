package com.baba.babaisyou.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

/**
 * Classe qui représente le menu principal du jeu
 */
public class MainView extends Application {

    public static int width;
    public static int height;
    public static Scene scene;
    private static StackPane root;
    private static Button resumeBtn;

    /**
     * @see javafx.application.Application#launch(String...) 
     */
    public static void show(String[] args) {
        launch(args);
    }

    /**
     * @see javafx.application.Application#start(Stage) 
     */
    @Override
    public void start(Stage stage) {
        root = new StackPane();
        resumeBtn = new Button("Reprendre");
        Button playBtn = new Button("Commencer");
        Button builderBtn = new Button("Constructeur de niveau");
        Button levelsBtn = new Button("Niveaux");
        Button quitBtn = new Button("Quitter");
        Label title = new Label("Baba is you");
        title.getStyleClass().add("title");

        //La première fois que le jeu est lancé, on veut ne veut pas afficher le bouton "reprendre"
        File currentLevel = new File("src/main/resources/com/baba/babaisyou/levels/currentLevel.txt");
        VBox vb;
        vb = new VBox(title, resumeBtn, playBtn, levelsBtn, builderBtn, quitBtn);
        if (!currentLevel.exists()) {
            resumeBtn.setVisible(false);
        }

        vb.setAlignment(Pos.CENTER);
        root.getChildren().add(vb);

        //Fenêtre deux fois moins grande que l'écran
        width = (int) Screen.getPrimary().getBounds().getWidth()/2;
        height = (int) Screen.getPrimary().getBounds().getHeight()/2;

        //Initialisation de la scène au départ de l'application
        scene = new Scene(root, MainView.width, MainView.height);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());

        //Gestion des évènements sur les boutons
        resumeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelView.show(stage, "currentLevel");
            }
        });
        playBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelView.show(stage, "level1");
            }
        });
        builderBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LevelBuilderView.show(stage);
            }
        });
        levelsBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { SelectionView.show(stage); }
        });
        quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        //Initialisation du Stage
        stage.setScene(scene);
        stage.setTitle("BabaIsYou");
        stage.getIcons().add(new Image("file:src/main/resources/com/baba/babaisyou/views/Baba.png"));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     * Méthode qui affiche la scène
     */
    public static void show() {
        scene.setRoot(root);
        scene.getStylesheets().add((new File("src/menu.css")).toURI().toString());

        File currentLevel = new File("src/main/resources/com/baba/babaisyou/levels/currentLevel.txt");

        resumeBtn.setVisible(currentLevel.exists());
    }

    /**
     * Getter scene
     * @return La scène
     */
    public static Scene getScene() {
        return scene;
    }
}
