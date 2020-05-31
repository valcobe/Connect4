package com.example.connect4.Logic;

public class Direction {

    public static final Direction DOWN = new Direction(1, 0);
    public static final Direction RIGHT = new Direction(0, 1);
    public static final Direction MAIN_DIAGONAL = new Direction(1, 1);
    public static final Direction CONTRA_DIAGONAL = new Direction(1, -1);

    public static final Direction[] ALL = new Direction[] {
            RIGHT, DOWN, MAIN_DIAGONAL, CONTRA_DIAGONAL
    };

    private final int changeInRow;
    private final int changeInColumn;

    private Direction(int changeInRow, int changeInColumn) {
        this.changeInColumn = changeInColumn;
        this.changeInRow = changeInRow;
    }

    public int getChangeInRow() {
        return this.changeInRow;
    }

    public int getChangeInColumn() {
        return this.changeInColumn;
    }

    public Direction invert() {
        int changeInRowInverted = this.changeInRow;
        int changeInColumnInverted = this.changeInColumn;
        changeInRowInverted *= -1;
        changeInColumnInverted *= -1;
        Direction inverted = new Direction(changeInRowInverted, changeInColumnInverted);
        return inverted;
    }

}
