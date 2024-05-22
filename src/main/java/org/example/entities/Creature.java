package org.example.entities;


import org.example.Cell;
import org.example.Map;

import java.util.ArrayDeque;

public abstract class Creature extends Entity{
    protected int speed;
    protected int attackPower;
    protected Cell targetCell;
    protected Entity targetEntity;
    protected ArrayDeque<Cell> path = new ArrayDeque<>();

    public abstract void makeMove(Map map);
    protected abstract String defineSign();

    public int getSpeed() {
        return speed;
    }
}
