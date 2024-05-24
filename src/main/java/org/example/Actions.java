package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.Cell;
import org.example.entities.Creature;

import org.example.entities.Impl.Grass;
import org.example.entities.Impl.Hervibore;
import org.example.entities.Map;
import org.example.services.RenderService;
import org.example.utils.EntityFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;

public class Actions {
    public static final Logger logger = LogManager.getLogger();
    public static final int numAddHervibore = 2;
    public static final int numAddGrass = 2;


    public static void addGrass(Map map){
        var entityFactory = map.getEntityFactory();
        var entitiesMap = map.getEntitiesMap();
        var statMap = EntityFactory.getTypesStats(entitiesMap.values().stream().toList());
//        logger.debug(statMap);

        if (statMap.get(Grass.class) == null) {
            var emptyCells = map.getEmptyCells();
            Collections.shuffle(emptyCells);
            int emptyAmount = emptyCells.size();
            logger.debug(String.format("Found %d empty cells, add %d grass cells", emptyAmount, Math.min(numAddGrass, emptyAmount)));
            for (int i = 0; i < Math.min(numAddGrass, emptyAmount); i++) {
                var grass = entityFactory.createGrass();
                grass.setCell(emptyCells.get(i));
                map.getEntitiesMap().put(emptyCells.get(i), grass);
            }
        }

    }


    public static void addHervibore(Map map){

        var entityFactory = map.getEntityFactory();
        var entitiesMap = map.getEntitiesMap();
        var statMap = EntityFactory.getTypesStats(entitiesMap.values().stream().toList());
//        logger.debug(statMap);

        if (statMap.get(Hervibore.class) == null) {

            var emptyCells = map.getEmptyCells();
            Collections.shuffle(emptyCells);

            int emptyAmount = emptyCells.size();
            logger.debug(String.format("Found %d empty cells, add %d hervibores", emptyAmount, Math.min(numAddHervibore, emptyAmount)));
            for (int i = 0; i < Math.min(numAddHervibore, emptyAmount); i++) {
                var hervi = entityFactory.createHervibore();
                hervi.setCell(emptyCells.get(i));
                map.getEntitiesMap().put(emptyCells.get(i), hervi);
            }
        }

    }


    public static void removeDeadEntities(Map map) {
        var entitiesMap = map.getEntitiesMap();
        var copyMap = new HashMap<>(entitiesMap);
        // clearing map
        for (var entry : copyMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (!entry.getValue().isAlive()) {
                logger.debug("Remove from map: " + entry.getValue());
                entitiesMap.put(entry.getKey(), null);
            }
        }

    }


    public static void moveAll(Map map, Writer renderWriter) throws IOException {

        for (Creature creature : map.getCreatures()) {
            removeDeadEntities(map);
            creature.makeMove(map);
        }

        addGrass(map);
        addHervibore(map);

        String newState = RenderService.renderMap(map);
        renderWriter.write(newState);
        renderWriter.flush();

    }

    public static void initActions(Map map, Writer renderWriter) throws IOException{
        renderWriter.write(RenderService.renderMap(map));
        renderWriter.flush();
    }


}
