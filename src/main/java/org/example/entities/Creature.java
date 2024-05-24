package org.example.entities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.services.Impl.AStarPathFindService;

import java.util.ArrayDeque;

public abstract class Creature extends Entity {
    protected int speed;
    protected int attackPower;
    protected Class<?> targetClass;
    protected Cell targetCell;
    protected Entity targetEntity;
    protected ArrayDeque<Cell> path = new ArrayDeque<>();
    private final Logger logger = LogManager.getLogger();

    public void makeMove(Map map) {

        if (!this.isAlive()) {
            return;
        }
        logger.debug("Im " + this + " at " + cell);

        // если цели нет или она мертва (здоровье 0, т.е. будет очищена в конце хода), то
        // ищем цель и прокладываем путь
        if (targetEntity == null || !AStarPathFindService.isTargetExist(targetEntity, targetCell, map)) {

            Object[] targetPair = AStarPathFindService.findNearestTargetStreamAPI(targetClass, cell, map);

            if (targetPair == null) {
                return;
            }

            targetEntity = (Entity) targetPair[0];
            targetCell = (Cell) targetPair[1];
            path = AStarPathFindService.findPath(cell, targetCell, map);

            logger.debug("Found target: " + targetEntity + " at " + targetCell);
            logger.debug("Path: " + path);
        }

        if (path == null) {
            logger.debug("Path is null");
            return;
        }

        // идём к цели
        for (int i = 0; i < path.size(); i++) {

            Cell nextCell = path.peek();
            Entity nextCellEntity = map.getEntitiesMap().get(nextCell);

            // останавливаемся и едим
            if (nextCell.equals(targetCell)) {
                logger.debug("Reduce health of " + targetEntity + " on " + attackPower);
                targetEntity.reduceHealth(attackPower);
                break;
            }

            if (nextCellEntity instanceof Obstacle) {
                logger.error(String.format("Next cell %s is obstacle: %s", nextCell, nextCellEntity));
            }

            // если следующей клетке Creature
            if (nextCellEntity instanceof Creature) {
                logger.debug(String.format("Step %d, next cell %s is: %s", i, nextCell, nextCellEntity));
                path = AStarPathFindService.findPath(cell, targetCell, map);
                break;
            }

            // если закончились шаги, останавливаемся
            if (i == speed) {
                path = AStarPathFindService.findPath(cell, targetCell, map);
                break;
            }

            // продвигаемся на одну ячейку
            map.getEntitiesMap().put(cell, null);
            cell = path.poll();
            map.getEntitiesMap().put(cell, this);
            logger.debug(this + " moved to " + cell);
        }

    }


    protected abstract String defineSign();

    public int getSpeed() {
        return speed;
    }
}
