package org.example.entities.Impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.*;
import org.example.config.Signs;
import org.example.services.Impl.AStarPathFindService;

public class Hervibore extends Creature {
    public static final int maxHealth = 5;
    public static final int maxSpeed = 5;
    private static final int attackPower = 1;
    private final Logger logger = LogManager.getLogger();

    public Hervibore(int health, int speed) {
        this.health = health;
        this.speed = speed;
        this.sign = defineSign();
    }

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
    public void makeMove(Map map) {

        if (!this.isAlive()) {
            return;
        }

        // если цели нет или она мертва (здоровье 0, т.е. будет очищена в конце хода), то
        // ищем цель и прокладываем путь
        if (targetEntity == null || !AStarPathFindService.isTargetExist(targetEntity, targetCell, map)) {

            Object[] targetPair = AStarPathFindService.findNearestTargetStreamAPI(Plant.class, cell, map);

            if (targetPair == null) {
                return;
            }
            logger.debug("Cell" + cell);
            targetEntity = (Entity) targetPair[0];
            targetCell = (Cell) targetPair[1];
            logger.debug("Found target: " + targetEntity);
            path = AStarPathFindService.findPath(cell, targetCell, map);
        }

        // тупо проверка для отладки
        if (path == null) {
            throw new RuntimeException("Path is null");
        }

        // идём к цели
        for (int i = 0; i < path.size(); i++) {
            // останавливаемся и едим
            if (path.peek().equals(targetCell)) {
                logger.info("REDUCE " + targetEntity);
                targetEntity.reduceHealth(attackPower);
            }

            // если столкнулись с препятствием или закончились шаги, останавливаемся
            if (map.getEntitiesMap().get(cell) instanceof Obstacle || i == speed-1){
                break;
            }

            // продвигаемся на одну ячейку
            map.getEntitiesMap().put(cell, null);
            cell = path.getFirst();
            map.getEntitiesMap().put(cell, this);

        }

    }


    @Override
    public String toString() {
        return String.format("Hervi{%s,+%s,->%s}", sign, health, speed);
    }
}
