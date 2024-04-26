package org.example;

public class Cell implements ICell{
    private final int x;
    private final int y;
    private final int[] coordinates;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.coordinates = new int[]{x, y};
    }




    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int[] getCoordinates() {
        return coordinates;
    }
}
