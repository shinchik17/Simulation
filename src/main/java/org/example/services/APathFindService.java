package org.example.services;

import org.example.Cell;
import org.example.Map;
import org.example.entities.Entity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;


public abstract class APathFindService {

    /**
     * Возвращает массив Object[2], где первый элемент - объект-цель(Entity),
     * а второй элемент - его объект-ячейка (Cell), в которой он находится.
     * Быстрее при размерах карты более 1500х1500. Реализован на цикле.
     *
     * @return массив Object[2]
     */
    public static Object[] findNearestTarget(Class<?> targetClass, Cell startCell, Map map) {
        Cell targetCell = null;
        Entity targetEntity = null;
        double minDistance = Double.MAX_VALUE;
        for (var entry : map.getEntitiesMap().entrySet()) {
            Cell cell = entry.getKey();
            Entity entity = entry.getValue();
            if (targetClass.isInstance(entity)) {
                if (cell.calcDistance(startCell) < minDistance) {
                    targetCell = cell;
                    targetEntity = entity;
                    minDistance = cell.calcDistance(startCell);
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
     * Возвращает массив Object[2], где первый элемент - объект-цель(Entity),
     * а второй элемент - его объект-ячейка (Cell), в которой он находится.
     * Быстрее оригинального метода при картах размером менее 1500х1500.
     * Реализован на Stream API
     *
     * @return массив Object[2]
     */
    // потренировался со Stream API - переписал верхнюю функцию
    public static Object[] findNearestTargetStreamAPI(Class<?> targetClass, Cell startCell, Map map) {
        Entry<Cell, Double> target_pair = map.getEntitiesMap().entrySet().stream()
                .filter(x -> targetClass.isInstance(x.getValue()))
                .collect(Collectors.toMap(
                        k -> k.getKey(),
                        v -> startCell.calcDistance(v.getKey())))
                .entrySet().stream().min(Entry.comparingByValue())
                .stream().findFirst().orElse(null);

        if (target_pair == null) {
            return null;
        } else {
            return new Object[]{map.getEntitiesMap().get(target_pair.getKey()), target_pair.getKey()};
        }
    }


    // TODO: проверить потом будет ли сравнение "==" работать как надо? или нужно будет менять на equals
    public boolean isTargetExist(Entity target, Cell targetCell, Map map) {
        return map.getEntitiesMap().get(targetCell) == target;
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


    public static Object[] findPath(Class<?> targetClass, Cell startCell, Map map){
        return null;
    };


}
