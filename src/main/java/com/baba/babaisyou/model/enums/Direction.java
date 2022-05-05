package com.baba.babaisyou.model.enums;

/**
 * Directions dans lesquelles un objet sur la map peut se déplacer
 */
public enum Direction {
    UP(0, -1, 0),
    DOWN(0, 1, 1),
    LEFT(-1, 0, 2),
    RIGHT(1, 0, 3),
    NONE(0, 0, 4);

    public final int dX, dY, index;

    Direction(int dX, int dY, int index) {
        this.dX = dX;
        this.dY = dY;
        this.index = index;
    }

    public Direction reverseDirection() {

        for (Direction direction : Direction.values()) {
            if (direction.dX == -dX && direction.dY == -dY) {
                return direction;
            }
        }

        return Direction.NONE;
    }
}
