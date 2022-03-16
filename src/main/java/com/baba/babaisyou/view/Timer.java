package com.baba.babaisyou.view;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.Position;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Classe qui permet d'animer le programme
 */
public class Timer extends AnimationTimer {

    private final Image img;

    public Timer()
    {
        img = new Image("file:src/main/resources/com/baba/babaisyou/views/Floor.png");
    }

    @Override
    public void handle(long now) {
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        Grid gridInstance = Grid.getInstance();
        GridPane gridPane = View.getGridPane();

        ArrayList<Position> movedPos = Object.getMovedPos();

        if (movedPos.size() != 0) {
            for (Position pos : movedPos) {
                StackPane stackPane = new StackPane();
                for (Object object : grid[pos.getY()][pos.getX()]) {
                    if (object.getMaterial() == Material.Floor) {
                        stackPane.getChildren().add(new ImageView(img));
                    } else {
                        stackPane.getChildren().add(new ImageView(object.getMaterial().getFrames()[0]));
                    }
                }
                gridPane.add(stackPane, pos.getX(), pos.getY());
            }
        }

        Object.resetMovedPos(); // reset à cet endroit permet d'éviter d'avoir des doublons lors de touches successives trop rapides.
    }
}
