package com.baba.babaisyou.view;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class Frames {

    private static final Image spriteSheetImage = new Image("file:src/main/resources/com/baba/babaisyou/views/sheet.png");
    private static final int frameSize = 24;

    /**
     * Créer une frame à partir d'une spritesheet
     * @param row position début frame
     * @param col position début frame
     * @param nbrFrames Nombre de frames
     * @return La frame complète
     */
    public static WritableImage[] getFrames(int row, int col, int nbrFrames) {
        WritableImage[] frames = new WritableImage[nbrFrames];
        PixelReader pixelReader = spriteSheetImage.getPixelReader();

        for (int i = 0; i < nbrFrames; i++) {
            frames[i] = new WritableImage(pixelReader, col*frameSize, row*frameSize + i*frameSize, frameSize, frameSize);
        }

        return frames;
    }

    /**
     * Créer une frame à partir d'une image
     * @param url L'url de l'image
     * @return La frame
     */
    public static WritableImage[] getImages(String url) {
        Image test = new Image(url, 24, 24, false, false);

        WritableImage[] frames = new WritableImage[1];
        PixelReader pixelReader = test.getPixelReader();

        frames[0] = new WritableImage(pixelReader, 24, 24);

        return frames;
    }
}

