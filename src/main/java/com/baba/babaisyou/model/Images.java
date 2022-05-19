package com.baba.babaisyou.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class Images {

    private static final Image spriteSheetImage = new Image("file:src/main/resources/com/baba/babaisyou/views/sheet.png");
    private static final int ImageSize = 24;

    /**
     * Créer la liste d'images à partir d'une SpriteSheet
     * @param row position y de la première image
     * @param col position x de la première image
     * @param nbrImages Nombre d'images.
     * @return La liste d'images.
     */
    public static WritableImage[] getImages(int row, int col, int nbrImages) {
        WritableImage[] images = new WritableImage[nbrImages];
        PixelReader pixelReader = spriteSheetImage.getPixelReader();

        for (int i = 0; i < nbrImages; i++) {
            images[i] = new WritableImage(pixelReader, col* ImageSize, row* ImageSize + i* ImageSize, ImageSize, ImageSize);
        }

        return images;
    }

    /**
     * Créer une liste d'images d'une seule image à partir d'un path.
     * @param imagePath Le path de l'image
     * @return La liste d'images contentment une image.
     */
    public static WritableImage[] getImages(String imagePath) {
        Image test = new Image(imagePath, 24, 24, false, false);

        WritableImage[] images = new WritableImage[1];
        PixelReader pixelReader = test.getPixelReader();

        images[0] = new WritableImage(pixelReader, 24, 24);

        return images;
    }
}

