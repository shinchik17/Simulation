package org.example.entities;




public abstract class Creature extends Entity{

    protected int speed;
    protected int health;

    public abstract void makeMove();
    protected abstract String defineSign();

    public boolean isAlive(){
        return health > 0;
    }

    public int getSpeed() {
        return speed;
    }
}
