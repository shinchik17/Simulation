package org.example.entities;


import org.example.Cell;

public abstract class Entity {
    protected String sign;
    protected int health=1;
    protected Cell cell;

    public String getSign() {
        return sign;
    }

    public void setCell(Cell cell){
        this.cell = cell;
    }

    public boolean isAlive(){
        return health > 0;
    }

    public void reduceHealth(int damage){
        health = Math.max(0, health - damage);
    }

}
