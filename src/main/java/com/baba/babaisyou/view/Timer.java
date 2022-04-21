package com.baba.babaisyou.view;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.Position;
import com.baba.babaisyou.presenter.Grid;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Classe qui permet d'animer le programme
 */
public class Timer extends AnimationTimer {

    @Override
    public void handle(long now) {
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        GridPane root = LevelView.getRoot();

        ArrayList<Position> movedPos = Object.getMovedPos();

        for (Position pos : movedPos) {

            StackPane stackPane = new StackPane();

            for (Object object : grid[pos.getY()][pos.getX()]) {

                ImageView iv = new ImageView(object.getMaterial().getFrames()[0]);
                iv.setPreserveRatio(true);
                iv.setFitHeight(Math.min(LevelView.getTileHeight(), LevelView.getTileWight()));

                stackPane.getChildren().add(iv);
            }
                root.add(stackPane, pos.getX(), pos.getY());
        }

        Object.resetMovedPos(); // reset à cet endroit permet d'éviter d'avoir des doublons lors de touches successives trop rapides.
    }
}
