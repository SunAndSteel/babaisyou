package com.baba.babaisyou.view;

import com.baba.babaisyou.model.ArrayOfObject;
import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.Position;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Grid;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;

public class Timer extends AnimationTimer {
    @Override
    public void handle(long now) {
        ArrayOfObject[][] grid = Grid.getInstance().grid;
        Grid gridInstance = Grid.getInstance();
        GraphicsContext gc = View.getGraphicsContext();

        ArrayList<Position> movedPos = Object.getMovedPos();
//        if (movedPos.size() != 0) {
//            for (Position pos : movedPos) {
//                for (Object object : grid[pos.getY()][pos.getX()]) {
////                    gc.drawImage(new Image(object.getMaterial().getImageUrl()), object.getX() * 32, object.getY() * 32);
//                    if (object.getMaterial() == Material.Floor) {
//                        gc.drawImage(new Image("file:src/main/resources/com/baba/babaisyou/views/Floor.png"), object.getX()*24, object.getY()*24);
//                    } else {
//                        gc.drawImage(object.getMaterial().getFrames()[0], object.getX() * 24, object.getY() * 24);
//                    }
//                }
//            }
//        }

        Object.resetMovedPos(); // reset à cet endroit permet d'éviter d'avoir des doublons lors de touches successives trop rapides.


        long index = (now - View.getStartTime()) / 200_000_000;

        for (ArrayOfObject[] arrayOfObjects : grid) {
            for (ArrayOfObject objects : arrayOfObjects) {
                for (Object object : objects) {
                    if (object.getMaterial() == Material.Floor) {
                        gc.drawImage(new Image("file:src/main/resources/com/baba/babaisyou/views/Floor.png"), object.getX()*24, object.getY()*24);
                    } else {
                        WritableImage[] frames = object.getMaterial().getFrames();
                        WritableImage image = frames[(int) index % frames.length];
                        gc.drawImage(image, object.getX() * 24, object.getY() * 24);
                    }

                }
            }
        }
    }
}
