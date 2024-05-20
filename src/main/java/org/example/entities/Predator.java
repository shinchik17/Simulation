package org.example.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.Signs;

public class Predator extends Creature{
    public static final int maxHealth = 3;
    public static final int maxSpeed = 5;
    public static final int maxAttackPower = 5;
    private final Logger logger = LogManager.getLogger();
    private final int attackPower;

    public Predator(int health, int speed, int attackPower){
        this.health = health;
        this.speed = speed;
        this.attackPower = attackPower;
        this.sign = defineSign();
    }

    protected String defineSign() {
        if (speed < maxSpeed / 2) {
            return Signs.PREDATOR[0];

        } else if (speed == maxSpeed / 2 || speed == maxSpeed / 2 + 1) {
            return Signs.PREDATOR[1];
        } else if (speed >= maxSpeed / 2 + 1) {
            return Signs.PREDATOR[2];
        } else {
            logger.debug("Unhandled clause");
        }

        return null;
    }

    public void attack(){

    }


    @Override
    public void makeMove() {

    }

    @Override
    public String toString() {
        return "Carnivore{" + speed + sign + '}';
    }
}
