package com.baba.babaisyou.model.enums;

import com.baba.babaisyou.view.Frames;
import javafx.scene.image.WritableImage;

/**
 * Textures qui peuvent être attribuées aux objets
 */
public enum Material {
    Baba("Ba", null, null, Frames.getFrames(1, 0, 1)),
    Floor("  ", null, null, Frames.getFrames(0, 0, 1)),
    Flag("Fg", null, null, Frames.getFrames(2, 0, 1)),
    Wall("Wl", null, null, Frames.getFrames(2, 0, 1)),
    Rock("RR", null, null, Frames.getFrames(2, 0, 1)),
    Is("Is", null, null, Frames.getFrames(2, 0, 1)),
    Push("Ps", Effects.Movable, null, Frames.getFrames(2, 0, 1)),
    Kill("Ki", Effects.Killer, null, Frames.getFrames(2, 0, 1)),
    Win("Wi", Effects.Winner, null, Frames.getFrames(2, 0, 1)),
    Stop("St", Effects.Hittable, null, Frames.getFrames(2, 0, 1)),
    You("Yo", Effects.Player, null, Frames.getFrames(2, 0, 1)),
    TextWall("TW", null, "Wall", Frames.getFrames(2, 0, 1)),
    TextBaba("TB", null, "Baba", Frames.getFrames(2, 0, 1)),
    TextFlag("TF", null, "Flag", Frames.getFrames(2, 0, 1)),
    TextRock("TR", null, "Rock", Frames.getFrames(2, 0, 1)),

    //Titre
    Title1("", null, null, Frames.getFrames(17, 17, 1)),
    Title2("", null, null, Frames.getFrames(17, 18, 1)),
    Title3("", null, null, Frames.getFrames(17, 19, 1)),
    Title4("", null, null, Frames.getFrames(17, 20, 1)),
    Title5("", null, null, Frames.getFrames(18, 17, 1)),
    Title6("", null, null, Frames.getFrames(18, 18, 1)),
    Title7("", null, null, Frames.getFrames(18, 19, 1)),
    Title8("", null, null, Frames.getFrames(18, 20, 1)),
    Title9("", null, null, Frames.getFrames(19, 17, 1)),
    Title10("", null, null, Frames.getFrames(19, 18, 1)),
    Title11("", null, null, Frames.getFrames(19, 19, 1)),
    Title12("", null, null, Frames.getFrames(19, 20, 1)),
    Title13("", null, null, Frames.getFrames(20, 17, 1)),
    Title14("", null, null, Frames.getFrames(20, 18, 1)),
    Title15("", null, null, Frames.getFrames(20, 19, 1)),
    Title16("", null, null, Frames.getFrames(20, 20, 1));


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