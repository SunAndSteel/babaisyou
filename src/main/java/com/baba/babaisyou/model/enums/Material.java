package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.view.Frames;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    Baba("Ba", null, null, Frames.getFrames(0, 0, 4)),
    Floor("  ", null, null, Frames.getFrames(51, 15, 1)),
    Flag("Fg", null, null, Frames.getFrames(59, 0, 4)),
    Wall("Wl", null, null, Frames.getFrames(59, 0, 4)),
    Rock("RR", null, null, Frames.getFrames(59, 0, 4)),
    Is("Is", null, null, Frames.getFrames(59, 0, 4)),
    Push("Ps", Effects.Movable, null, Frames.getFrames(59, 0, 4)),
    Kill("Ki", Effects.Killer, null, Frames.getFrames(59, 0, 4)),
    Win("Wi", Effects.Winner, null, Frames.getFrames(59, 0, 4)),
    Stop("St", Effects.Hittable, null, Frames.getFrames(59, 0, 4)),
    You("Yo", Effects.Player, null, Frames.getFrames(59, 0, 4)),
    TextWall("TW", null, "Wall", Frames.getFrames(59, 0, 4)),
    TextBaba("TB", null, "Baba", Frames.getFrames(59, 0, 4)),
    TextFlag("TF", null, "Flag", Frames.getFrames(59, 0, 4)),
    TextRock("TR", null, "Rock", Frames.getFrames(59, 0, 4));

    private final String str;
    private final Effects effect;
    private final String nameObjectAffected;
    private final WritableImage[] frames;

    Material(String str, Effects effect, String nameObjectAffected, WritableImage[] frames) {
        this.str = str;
        this.effect = effect;
        this.nameObjectAffected = nameObjectAffected;
        this.frames = frames;
    }

    public String toString() { return str; }

    public Effects getEffect() { return effect; }

    public String getNameObjectAffected() { return nameObjectAffected; }

    public WritableImage[] getFrames() { return frames; }
}