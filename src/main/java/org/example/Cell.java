package org.example;

import java.util.Arrays;
import java.util.Objects;

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

    public double calcDistance(Cell targetCell){
        return Math.sqrt(Math.pow(x-targetCell.getX(), 2) +
                Math.pow(y-targetCell.getY(), 2));
    }


    @Override
    public String toString() {
        return String.format("Cell{%d, %d}", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
