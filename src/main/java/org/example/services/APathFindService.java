package org.example.services;

import org.example.Cell;
import org.example.Map;
import org.example.entities.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class APathFindService implements PathFindService {

//    public static double calcDistance(Cell startCell, Cell targetCell){
//        return Math.sqrt(Math.pow(startCell.getX()-targetCell.getX(), 2) -
//                Math.pow(startCell.getY()-targetCell.getY(), 2))
//    }


    /**
     * Возвращает массив Object[2], где первый элемент - объект-цель(Entity),
     * а второй элемент - его объект-ячейка (Cell), в которой он находится.
     *
     * @return массив Object[2]
     */
    public static Object[] findNearestTarget(Class<?> targetClass, Cell startCell, Map map) {

        Cell targetCell = null;
        Entity targetEntity = null;
        double minDistance = Double.MAX_VALUE;
        // TODO: наверняка это можно переписать черезе Stream API
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

    @Override
    public boolean isTargetExists(Entity target, Cell targetCell, Map map) {
        return map.getEntitiesMap().get(targetCell) == target;
    }


    @Override
    public ArrayDeque<Cell> findPath(Cell targetCell, Cell startCell, Map map) {
        return null;
    }

    /**
     * Находит и возвращает лист соседних клеток для заданной
     * @param cell клетка, для которой искать соседей
     * @param map карта мира
     * @return лист соседних клеток для cell
     */
    public static List<Cell> getAdjacentCells(Cell cell, Map map) {
        double MAX_DISTANCE = 1.5;

        return map.getEntitiesMap().keySet().stream()
                .filter(x -> cell.calcDistance(x) < MAX_DISTANCE && !x.equals(cell)).toList();



    }

}
