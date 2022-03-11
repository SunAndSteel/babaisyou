package com.baba.babaisyou.model.enums;

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
