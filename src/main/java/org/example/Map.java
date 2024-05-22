package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.Entity;
import org.example.entities.Impl.*;
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

    public Map(int size){
        this.width = size;
        this.height = size;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                entitiesMap.put(new Cell(x, y), null);
            }
        }

        entitiesMap.put(new Cell(0, 0), null);
        entitiesMap.put(new Cell(1, 0), new Predator(3, 2, 3));
        entitiesMap.put(new Cell(2, 0), null);
        entitiesMap.put(new Cell(0, 1), null);
        entitiesMap.put(new Cell(0, 2), new Rock());
        entitiesMap.put(new Cell(1, 1), new Grass());
        entitiesMap.put(new Cell(2, 1), null);
        entitiesMap.put(new Cell(2, 2), new Hervibore(5,1));
        entitiesMap.put(new Cell(1, 2), new Tree());

    }


    public Map(int width, int height){

        this.width = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = new Cell(x, y);
                Entity entity = entityFactory.getRandomEntity();
                if (entity != null){
                    entity.setCell(cell);
                }
                entitiesMap.put(cell, entity);
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
