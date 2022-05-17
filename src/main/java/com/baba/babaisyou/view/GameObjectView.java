package com.baba.babaisyou.view;

import com.baba.babaisyou.model.GameObject;
import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Material;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

public class GameObjectView extends ImageView {

    private static final int imageSize = 24;
    private static final Image spriteSheetImage = new Image("file:src/main/resources/com/baba/babaisyou/views/sheet.png");
    private static final Map<GameObject, GameObjectView> objectImageView = new HashMap<>();
    private static final ColorAdjust brightnessAdjust = new ColorAdjust(0, 0, -0.6, 0);
    private static final Glow glowEffect = new Glow(0.2);

    private final GameObject object;
    private Material material = null;
    private Direction facing = Direction.UP;


    public GameObjectView(GameObject object) {
        super();

        this.object = object;

        if (material.isDirectional()) {
            facing = Direction.RIGHT;
        }

        checkImage();

        objectImageView.put(object, this);

        setPreserveRatio(true);
        setFitHeight(LevelView.getTileSize());

        if (material.hasEffect() || material.hasNameObject() || material == Material.Is) {
            setEffect(brightnessAdjust);
        }
    }

    public void checkImage() {
        if (material != object.getMaterial()) {

            material = object.getMaterial();
            PixelReader pixelReader = spriteSheetImage.getPixelReader();

            if (material.isDirectional()) {
                facing = Direction.RIGHT;
            } else {
                facing = Direction.UP;
            }

            int x = (material.getImageX() + facing.index) * imageSize;
            int y = (material.getImageY() + facing.index) * imageSize;
            WritableImage image = new WritableImage(pixelReader, x, y, imageSize, imageSize);

            setImage(image);
        }
    }

    public void changeImageDirection(Direction direction) {

        if (material.isDirectional()) {

            facing = direction;

            PixelReader pixelReader = spriteSheetImage.getPixelReader();

            int x = (material.getImageX() + facing.index) * imageSize;
            int y = (material.getImageY() + facing.index) * imageSize;
            WritableImage image = new WritableImage(pixelReader, x, y, imageSize, imageSize);
        }

    }

    public static Map<GameObject, GameObjectView> getObjectImageView() {
        return objectImageView;
    }
}
