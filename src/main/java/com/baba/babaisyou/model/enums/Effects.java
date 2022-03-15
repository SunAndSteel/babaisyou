package com.baba.babaisyou.model.enums;

/**
 * Attributs qui peuvent être donnés aux objets
 */
public enum Effects {
    Movable("Push"),
    Player("You"),
    Killer("Kill"),
    Hittable("Stop"),
    Winner("Win");

    public final String materialName;

    Effects(String materialName) {
        this.materialName = materialName;
    }
}
