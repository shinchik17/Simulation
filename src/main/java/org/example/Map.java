package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.Entity;
import org.example.entities.Obstacle;
import org.example.utils.EntityFactory;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Map {

    private final HashMap<Cell, Entity> entitiesMap = new HashMap<>();
    private final EntityFactory entityFactory = new EntityFactory();
    private final int width;
    private final int height;
    private final Logger logger = LogManager.getLogger();

    public Map(int width, int height){
        this.width = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Entity entity = entityFactory.getRandomEntity();
                entitiesMap.put(new Cell(x, y), entity);
//                logger.debug(entity);
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public HashMap<Cell, Entity> getEntitiesMap() {
        return entitiesMap;
    }

    public HashMap<Cell, Boolean> getObstaclesMap(){
        return (HashMap<Cell, Boolean>) entitiesMap.entrySet().stream()
                .filter(x -> x.getValue() instanceof Obstacle)
                .collect(Collectors.toMap(
                        k -> k.getKey(),
                        v -> Boolean.TRUE));
    }

}
