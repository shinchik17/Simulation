package org.example.entities.Impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.Signs;
import org.example.entities.Creature;

public class Hervibore extends Creature {
    public static final int maxHealth = 5;
    public static final int maxSpeed = 5;
    private final Logger logger = LogManager.getLogger();

    public Hervibore(int health, int speed){
        this.health = health;
        this.speed = speed;
        this.sign = defineSign();
    }

    protected String defineSign(){
        if (speed < maxSpeed / 2){
            return Signs.HERBIVORE[0];

        } else if (speed == maxSpeed / 2 || speed == maxSpeed / 2 + 1) {
            return Signs.HERBIVORE[1];
        }
        else if (speed >= maxSpeed / 2 + 1){
            return Signs.HERBIVORE[2];
        }
        else {
            logger.debug("Unhandled clause");
        }

        return null;
    }



    @Override
    public void makeMove() {


    }

    @Override
    public String toString() {
        return "Hervibore{" + speed + sign + '}';
    }
}
