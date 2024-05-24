package org.shin.entities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shin.services.Impl.AStarPathFindService;

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

        // если цели нет или она мертва (здоровье 0, т.е. будет очищена в конце хода),
        // то ищем цель и прокладываем путь
        if (targetEntity == null || !AStarPathFindService.isTargetExist(targetEntity, targetCell, map)) {

            Object[] targetPair = AStarPathFindService.findNearestTargetStreamAPI(targetClass, cell, map);

            // если не существует валидных целей, скипаем
            if (targetPair == null) {
                return;
            }

            targetEntity = (Entity) targetPair[0];
            targetCell = (Cell) targetPair[1];
            path = AStarPathFindService.findPath(cell, targetCell, map);
            logger.debug("Found target: " + targetEntity + " at " + targetCell);
            logger.debug("Path: " + path);
        }

        // если путь почему-то null, заканчиваем ход
        if (path == null) {
            logger.debug("Path is null");
            return;
        }

        // если путь пустой, пытаемся проложить новый, в случае неудачи - заканчиваем ход
        if (path.isEmpty()) {
            logger.debug(String.format("Path is empty, targetCell: %s, targetEnt: %s", targetCell, targetEntity));
            path = AStarPathFindService.findPath(cell, targetCell, map);
            if (path == null){
                return;
            }
        }


        for (int i = 0; i < speed; i++) {

            assert path != null;
            assert !path.isEmpty();

            // берём клетку из очереди path, выясняем, что в ней за сущность
            Cell nextCell = path.poll();
            Entity nextCellEntity = map.getEntitiesMap().get(nextCell);

            // если там, что нам нужна, то останавливаемся и едим
            if (nextCell.equals(targetCell)) {
                logger.debug(String.format("%s eats of %s on %d", this, targetEntity, attackPower));
                targetEntity.reduceHealth(attackPower);
                break;
            }

            // если в ней препятствие (чего вообще-то не должно быть! но на всякий), перестраиваем путь и скипаем
            if (nextCellEntity instanceof Obstacle) {
                logger.error(String.format("Next cell %s is obstacle: %s", nextCell, nextCellEntity));
                path = AStarPathFindService.findPath(cell, targetCell, map);
                continue;
            }

            // если следующей клетке Creature, перестраиваем путь и скипаем
            if (nextCellEntity instanceof Creature) {
                logger.debug(String.format("Step %d/%d, next %s is: %s", i, speed-1, nextCell, nextCellEntity));
                path = AStarPathFindService.findPath(cell, targetCell, map);
                continue;
            }

            // если клетка пустая, то становимся в неё
            map.getEntitiesMap().put(cell, null);
            cell = nextCell;
            map.getEntitiesMap().put(cell, this);
            logger.debug(this + " moved to " + cell);
        }


    }


    protected abstract String defineSign();

}
