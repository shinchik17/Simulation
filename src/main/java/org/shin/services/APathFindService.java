package org.shin.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shin.entities.Entity;
import org.shin.entities.Cell;
import org.shin.entities.Map;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;


public abstract class APathFindService {
    private static final Logger logger = LogManager.getLogger();


    /**
     * Возвращает массив Object[2], где первый элемент - живой объект-цель(Entity),
     * а второй элемент - его объект-клетка (Cell), в которой он находится.
     * Быстрее при размерах карты более 1500х1500. Реализован на цикле.
     *
     * @return массив Object[2]
     */
    public static Object[] findNearestTarget(Class<?> targetClass, Cell start, Map map) {
        Cell targetCell = null;
        Entity targetEntity = null;
        double minDistance = Double.MAX_VALUE;
        for (var entry : map.getEntitiesMap().entrySet()) {
            Cell cell = entry.getKey();
            Entity entity = entry.getValue();
            if (targetClass.isInstance(entity) && entity.isAlive()) {
                if (cell.calcDistance(start) < minDistance) {
                    targetCell = cell;
                    targetEntity = entity;
                    minDistance = cell.calcDistance(start);
                }
            }
        }

        if (targetCell == null) {
            return null;
        } else {
            return new Object[]{targetEntity, targetCell};
        }
    }


    /**
     * Возвращает массив Object[2], где первый элемент - живой объект-цель(Entity),
     * а второй элемент - его объект-клетка (Cell), в которой он находится.
     * Быстрее оригинального метода при картах размером менее 1500х1500.
     * Реализован на Stream API
     *
     * @return массив Object[2]
     */
    // потренировался со Stream API - переписал верхнюю функцию
    public static Object[] findNearestTargetStreamAPI(Class<?> targetClass, Cell start, Map map) {
        Entry<Cell, Double> target_pair = map.getEntitiesMap().entrySet().stream()
                .filter(x -> targetClass.isInstance(x.getValue()))
                .filter(x -> x.getValue().isAlive())
                .collect(Collectors.toMap(
                        k -> k.getKey(),
                        v -> start.calcDistance(v.getKey())))
                .entrySet().stream().min(Entry.comparingByValue())
                .stream().findFirst().orElse(null);

        if (target_pair == null) {
            return null;
        } else {
//            logger.info(map.getEntitiesMap());
//            logger.info(target_pair.getKey());
//            logger.info(map.getEntitiesMap().get(target_pair.getKey()));

            return new Object[]{map.getEntitiesMap().get(target_pair.getKey()), target_pair.getKey()};
        }
    }


    /**
     * Находит и возвращает лист соседних клеток для заданной карты
     *
     * @param cell клетка, для которой искать соседей
     * @param map  карта мира
     * @return лист соседних клеток для cell
     */
    public static List<Cell> getAdjacentCells(Cell cell, Map map) {
        double MAX_DISTANCE = 1.5;  // расстояние до диагональных соседних клеток (~1.41)

        return map.getEntitiesMap().keySet().stream()
                .filter(x -> cell.calcDistance(x) < MAX_DISTANCE && !x.equals(cell)).toList();
    }


    /**
     * Находит и возвращает лист соседних клеток для заданного списка клеток
     *
     * @param cell клетка, для которой искать соседей
     * @param cells список всех клеток
     * @return лист соседних клеток для cell
     */
    public static List<Cell> getAdjacentCells(Cell cell, List<Cell> cells) {
        double MAX_DISTANCE = 1.5;  // расстояние до диагональных соседних клеток (~1.41)

        return cells.stream()
                .filter(x -> cell.calcDistance(x) < MAX_DISTANCE && !x.equals(cell))
                .toList();
    }


    /**
     * Проверяет существование выбранной цели и присутствие её в предполагаемой клетке.
     *
     * @param target цель
     * @param targetCell предполагаемое положение клетки
     * @param map карта
     * @return жива ли цель & находится в предполагаемой клетке
     */
    public static boolean isTargetExist(Entity target, Cell targetCell, Map map) {
        Entity _target = map.getEntitiesMap().get(targetCell);
        if (target != null) {
            return _target == target && _target.isAlive();
        }
        return false;
    }


}
