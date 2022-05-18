package com.baba.babaisyou.presenter;


import com.baba.babaisyou.view.LevelBuilderView;
import com.baba.babaisyou.view.LevelView;
import com.baba.babaisyou.view.MainView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Classe contenant la méthode main qui va exécuter le jeu
 */
public class Main {
    public static void main(String[] args) {
        MainView.show(args);
    }

    public static void builderBtnAction(Stage stage) {
        LevelBuilderView.show(stage);
    }

    public static void playBtnAction(Stage stage, Button playBtn) {
        if (Objects.equals(playBtn.getText(), "Niveaux")) {
            LevelView.show(stage, "level1");
        } else if (Objects.equals(playBtn.getText(), "Commencer")) {
            LevelView.show(stage, "level1");
        }
    }




}