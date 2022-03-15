package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.view.Frames;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    Baba("Ba", null, null, "file:src/main/resources/com/baba/babaisyou/views/Baba.png", Frames.getFrames(0, 0, 4)),
    Floor("  ", null, null, "file:src/main/resources/com/baba/babaisyou/views/Floor.png", Frames.getFrames(51, 15, 1)),
    Flag("Fg", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Wall("Wl", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Rock("RR", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Is("Is", null, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Push("Ps", Effects.Movable, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Kill("Ki", Effects.Killer, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Win("Wi", Effects.Winner, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    Stop("St", Effects.Hittable, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    You("Yo", Effects.Player, null, "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    TextWall("TW", null, "Wall", "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    TextBaba("TB", null, "Baba", "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    TextFlag("TF", null, "Flag", "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4)),
    TextRock("TR", null, "Rock", "file:src/main/resources/com/baba/babaisyou/views/Wall.png", Frames.getFrames(59, 0, 4));

    private final String str;
    private final Effects effect;
    private final String nameObjectAffected;
    private final String imageUrl;
    private final WritableImage[] frames;

    Material(String str, Effects effect, String nameObjectAffected, String imageUrl, WritableImage[] frames) {
        this.str = str;
        this.effect = effect;
        this.nameObjectAffected = nameObjectAffected;
        this.imageUrl = imageUrl;
        this.frames = frames;
    }

    public String toString() { return str; }

    public Effects getEffect() { return effect; }

    public String getNameObjectAffected() { return nameObjectAffected; }

    public String getImageUrl() { return imageUrl; }

    public WritableImage[] getFrames() { return frames; }
}