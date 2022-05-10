package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.view.Frames;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    //Objets
    Baba(null, null, Frames.getFrames(2, 0, 4), true),
    Floor(null, null, Frames.getFrames(2, 4, 1), false),
    Flag(null, null, Frames.getFrames(2, 2, 1), false),
    Wall(null, null, Frames.getFrames(2, 1, 1), false),
    Rock(null, null, Frames.getFrames(2, 3, 1), false),
    Button(null, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\coin.png"), false),
    Is(null, null, Frames.getFrames(0, 4, 1), false),
    Cursor(null, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\select1.png"), true),

    //Effets
    Push(Effect.Movable, null, Frames.getFrames(1, 3, 1), false),
    Kill(Effect.Killer, null, Frames.getFrames(2, 1, 1), false),
    Win(Effect.Winner, null, Frames.getFrames(1, 2, 1), false),
    Stop(Effect.Hittable, null, Frames.getFrames(1, 1, 1), false),
    You(Effect.Player, null, Frames.getFrames(1, 0, 1), false),
    Play(Effect.Play, null, Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\play.png"), false),

    //Textes
    TextWall(null, "Wall", Frames.getFrames(0, 1, 1), false),
    TextBaba(null, "Baba", Frames.getFrames(0, 0, 1), false),
    TextFlag(null, "Flag", Frames.getFrames(0, 2, 1), false),
    TextRock(null, "Rock", Frames.getFrames(0, 3, 1), false),
    TextBtn(null, "Button", Frames.getImages("file:src\\main\\resources\\com\\baba\\babaisyou\\views\\btn.png"), false);

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