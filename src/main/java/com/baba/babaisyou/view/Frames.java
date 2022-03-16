package com.baba.babaisyou.view;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.Arrays;

public class Frames {

    private static final Image spriteSheetImage = new Image("file:src/main/resources/com/baba/babaisyou/views/spriteSheet.png");

    public static WritableImage[] getFrames(int row, int col, int nbrFrames) {
        WritableImage[] frames = new WritableImage[nbrFrames];
        PixelReader pixelReader = spriteSheetImage.getPixelReader();

        for (int i = 0; i < nbrFrames; i++) {
            frames[i] = new WritableImage(pixelReader, col*24 + i*24, row*24, 24, 24);
        }

        return frames;
    }
}
