package com.baba.babaisyou.view;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.presenter.Grid;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Timer extends AnimationTimer {
    @Override
    public void handle(long now) {
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        GraphicsContext gc = View.getGraphicsContext();

        gc.drawImage(new Image("C:\\Users\\jerem\\IdeaProjects\\babaisyou\\src\\main\\resources\\com\\baba\\babaisyou\\views\\background.png"), 0, 0);

        for (ArrayOfObject[] row : grid) {
            for (ArrayOfObject arrayOfObject : row ) {
                for (Object object : arrayOfObject) {
                    gc.drawImage(new Image(object.getMaterial().getImageUrl()), object.getX() * 32, object.getY() * 32);

                }


            }
        }
    }
}
