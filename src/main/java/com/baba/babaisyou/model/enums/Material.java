package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.view.Frames;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    //Objets
    Baba(null, null, Frames.getFrames(2, 0, 4), true, 0, 2),
    Floor(null, null, Frames.getFrames(2, 4, 1), false, 4, 2),
    Flag(null, null, Frames.getFrames(2, 2, 1), false, 2, 2),
    Wall(null, null, Frames.getFrames(2, 1, 1), false, 1, 2),
    Rock(null, null, Frames.getFrames(2, 3, 1), false, 3, 2),
    Button(null, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\coin.png"), false, -1, -1),
    Is(null, null, Frames.getFrames(0, 4, 1), false, 4, 0),
    Cursor(null, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\select1.png"), false, -1, -1),


    //Effets
    Push(Effect.Movable, null, Frames.getFrames(1, 3, 1), false, 3, 1),
    Kill(Effect.Killer, null, Frames.getFrames(2, 1, 1), false, 1, 2),
    Win(Effect.Winner, null, Frames.getFrames(1, 2, 1), false, 2, 1),
    Stop(Effect.Hittable, null, Frames.getFrames(1, 1, 1), false, 1, 1),
    You(Effect.Player, null, Frames.getFrames(1, 0, 1), false, 0, 1),
    Play(Effect.Play, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\play.png"), false, -1, -1),

    //Textes
    TextWall(null, "Wall", Frames.getFrames(0, 1, 1), false, 1, 0),
    TextBaba(null, "Baba", Frames.getFrames(0, 0, 1), false, 0, 0),
    TextFlag(null, "Flag", Frames.getFrames(0, 2, 1), false, 2, 0),
    TextRock(null, "Rock", Frames.getFrames(0, 3, 1), false, 3, 0),
    TextBtn(null, "Button", Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\btn.png"), false, -1, -1);

    private final Effect effect;
    private final String nameObject;
    private final WritableImage[] frames;
    private final Boolean isDirectional;
    private final int imageX, imageY;

    Material(Effect effect, String nameObject, WritableImage[] frames, boolean isDirectional, int imageX, int imageY) {
        this.effect = effect;
        this.nameObject = nameObject;
        this.frames = frames;
        this.isDirectional = isDirectional;
        this.imageX = imageX;
        this.imageY = imageY;
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

    public int getImageX() { return imageX; }

    public int getImageY() { return imageY; }
}