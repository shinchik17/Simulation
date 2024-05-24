package org.shin.entities.Impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shin.config.Signs;
import org.shin.entities.Creature;
import org.shin.entities.Plant;

public class Hervibore extends Creature {
    public static final int maxHealth = 5;
    public static final int maxSpeed = 5;
    private final Logger logger = LogManager.getLogger();

    public Hervibore(int health, int speed) {
        this.health = health;
        this.speed = speed;
        this.sign = defineSign();
        this.targetClass = Plant.class;
        this.attackPower = 1;
    }

    // определяем тип травоядного в зависимости от значения скорости,
    // переданного при создании и присваиваем ему значок
    protected String defineSign() {
        if (speed < maxSpeed / 2) {
            return Signs.HERBIVORE[0];

        } else if (speed == maxSpeed / 2 || speed == maxSpeed / 2 + 1) {
            return Signs.HERBIVORE[1];
        } else if (speed >= maxSpeed / 2 + 1) {
            return Signs.HERBIVORE[2];
        } else {
            logger.debug("Unhandled clause");
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("Hervi{%s,+%d,->%d}", sign, health, speed);
    }
}
