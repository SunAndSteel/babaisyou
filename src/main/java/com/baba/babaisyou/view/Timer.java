package com.baba.babaisyou.view;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.Position;
import com.baba.babaisyou.presenter.Grid;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Classe qui permet d'animer le programme
 */
public class Timer extends AnimationTimer {
    @Override
    public void handle(long now) {
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        Grid gridInstance = Grid.getInstance();
        GraphicsContext gc = View.getGraphicsContext();

        ArrayList<Position> movedPos = Object.getMovedPos();
        if (movedPos.size() != 0) {
            for (Position pos : movedPos) {
                for (Object object : grid[pos.getY()][pos.getX()]) {
                    gc.drawImage(new Image(object.getMaterial().getImageUrl()), object.getX() * 32, object.getY() * 32);
                }
            }
        }
        Object.resetMovedPos(); // reset à cet endroit permet d'éviter d'avoir des doublons lors de touches successives trop rapides.
    }
}
