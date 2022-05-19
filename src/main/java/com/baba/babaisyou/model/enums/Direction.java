package com.baba.babaisyou.model.enums;

/**
 * Directions dans lesquelles un objet sur la map peut se déplacer.
 */
public enum Direction {
    RIGHT(1, 0, 0),
    DOWN(0, 1, 1),
    LEFT(-1, 0, 2),
    UP(0, -1, 3),
    NONE(0, 0, 4);

    public final int dX, dY, index;

    /**
     * Constructeur de direction.
     * @param dX de combien l'objet va bouger en x.
     * @param dY de combien l'objet va bouger en y.
     * @param index ordre de direction.
     */
    Direction(int dX, int dY, int index) {
        this.dX = dX;
        this.dY = dY;
        this.index = index;
    }

    /**
     * Permet de récupérer la direction inverse.
     * @return La direction inverse.
     */
    public Direction reverseDirection() {

        for (Direction direction : Direction.values()) {
            if (direction.dX == -dX && direction.dY == -dY) {
                return direction;
            }
        }

        return Direction.NONE;
    }
}
