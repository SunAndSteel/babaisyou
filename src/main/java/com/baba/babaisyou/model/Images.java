package com.baba.babaisyou.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class Images {

    private static final Image spriteSheetImage = new Image("file:src/main/resources/com/baba/babaisyou/views/sheet.png");
    private static final int ImageSize = 24;

    /**
     * Créer une frame à partir d'une spritesheet
     * @param row position début frame
     * @param col position début frame
     * @param nbrFrames Nombre de frames
     * @return La frame complète
     */
    public static WritableImage[] getImages(int row, int col, int nbrFrames) {
        WritableImage[] images = new WritableImage[nbrFrames];
        PixelReader pixelReader = spriteSheetImage.getPixelReader();

        for (int i = 0; i < nbrFrames; i++) {
            images[i] = new WritableImage(pixelReader, col* ImageSize, row* ImageSize + i* ImageSize, ImageSize, ImageSize);
        }

        return images;
    }

    /**
     * Créer une frame à partir d'une image
     * @param url L'url de l'image
     * @return La frame
     */
    public static WritableImage[] getImages(String url) {
        Image test = new Image(url, 24, 24, false, false);

        WritableImage[] images = new WritableImage[1];
        PixelReader pixelReader = test.getPixelReader();

        images[0] = new WritableImage(pixelReader, 24, 24);

        return images;
    }
}

