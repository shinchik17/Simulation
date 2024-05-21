package org.example.services.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Cell;
import org.example.Map;
import org.example.entities.Entity;
import org.example.services.APathFindService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BreadthFirstPathFindService extends APathFindService {
    public static final Logger logger = LogManager.getLogger();

    /**
     * Возвращает массив Object[2], где первый элемент - объект-цель(Entity),
     * а второй элемент - его объект-ячейка (Cell), в которой он находится.
     * Реализован посредством поиска в ширину. Медленный при картах больше ~10х10
     *
     * @return массив Object[2]
     */
    public static Object[] findNearestTarget(Class<?> targetClass, Cell startCell, Map map) {

        HashMap<Cell, Entity> entitiesMap = map.getEntitiesMap();
        List<Cell> cellsList = entitiesMap.keySet().stream().toList();

        ArrayDeque<Cell> queue = new ArrayDeque<>(getAdjacentCells(startCell, cellsList));
        ArrayList<Cell> searched = new ArrayList<>();

        while (!queue.isEmpty()) {
            Cell curCell = queue.pop();
            if (!searched.contains(curCell)) {
                if (targetClass.isInstance(entitiesMap.get(curCell))) {
                    return new Object[] {entitiesMap.get(curCell), curCell};
                } else {
                    searched.add(curCell);
                    queue.addAll(getAdjacentCells(curCell, cellsList));
                }
            }
        }

        return null;

    }


}
