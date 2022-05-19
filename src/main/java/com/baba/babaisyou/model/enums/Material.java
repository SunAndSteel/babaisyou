package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.model.Images;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    //Objets
    Baba(null, null, Images.getImages(2, 0, 4), true),
    Floor(null, null, Images.getImages(3, 2, 1), false),
    Flag(null, null, Images.getImages(2, 2, 1), false),
    Wall(null, null, Images.getImages(2, 1, 1), false),
    Rock(null, null, Images.getImages(2, 3, 1), false),
    Is(null, null, Images.getImages(3, 1, 1), false),
    Cursor(null, null, Images.getImages("file:src/main/resources/com/baba/babaisyou/views/select.png"), false),
    Water(null, null, Images.getImages(2, 4, 1), false),
    Grass(null, null, Images.getImages(2, 5, 1), false),
    StoneFloor(null, null, Images.getImages(3, 3, 1), false),
    Lava(null, null, Images.getImages(2, 6, 1), false),
    Door(null, null, Images.getImages(2, 7, 1), false),
    Key(null, null, Images.getImages(2, 8, 1), false),
    Skull(null, null, Images.getImages(2, 9, 1), false),

    //Effets
    Push(Effect.Movable, null, Images.getImages(1, 3, 1), false),
    Win(Effect.Winner, null, Images.getImages(1, 2, 1), false),
    Stop(Effect.Hittable, null, Images.getImages(1, 1, 1), false),
    You(Effect.Player, null, Images.getImages(1, 0, 1), false),
    Sink(Effect.Sink, null, Images.getImages(1, 4, 1), false),
    Float(Effect.Float, null, Images.getImages(3, 4, 1), false),
    Hot(Effect.Hot, null, Images.getImages(1, 6, 1), false),
    Melt(Effect.Melt, null, Images.getImages(3, 6, 1), false),
    Shut(Effect.Shut, null, Images.getImages(1, 7, 1), false),
    Open(Effect.Open, null, Images.getImages(1, 8, 1), false),
    Loose(Effect.Loose, null, Images.getImages(1, 9, 1), false),
    Defeat(Effect.Defeat, null, Images.getImages(3, 9, 1), false),

    //Textes
    TextWall(null, "Wall", Images.getImages(0, 1, 1), false),
    TextBaba(null, "Baba", Images.getImages(0, 0, 1), false),
    TextFlag(null, "Flag", Images.getImages(0, 2, 1), false),
    TextGrass(null, "Grass", Images.getImages(0, 5, 1), false),
    TextWater(null, "Water", Images.getImages(0, 4, 1), false),
    TextLava(null, "Lava", Images.getImages(0, 6, 1), false),
    TextDoor(null, "Door", Images.getImages(0, 7, 1), false),
    TextKey(null, "Key", Images.getImages(0, 8, 1), false),
    TextSkull(null, "Skull", Images.getImages(0, 9, 1), false),
    TextRock(null, "Rock", Images.getImages(0, 3, 1), false);

    private final Effect effect;
    private final String nameObject;
    private final WritableImage[] frames;
    private final Boolean isDirectional;

    Material(Effect effect, String nameObject, WritableImage[] frames, boolean isDirectional) {
        this.effect = effect;
        this.nameObject = nameObject;
        this.frames = frames;
        this.isDirectional = isDirectional;
    }


    public Effect getEffect() { return effect; }

    public String getNameObject() { return nameObject; }

    public WritableImage[] getFrames() { return frames; }

    public Boolean hasEffect() {
        return effect != null;
    }

    public Boolean hasNameObject() {
        return nameObject != null;
    }

    public Boolean isDirectional() {
        return isDirectional;
    }
}