package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.Impl.Cell;
import org.example.entities.Impl.Grass;
import org.example.entities.Impl.Hervibore;
import org.example.entities.Impl.Map;
import org.example.services.APathFindService;
import org.example.services.Impl.AStarPathFindService;
import org.example.services.RenderService;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {

//        for (int i = 0; i < Signs.ROCK.length; i++) {
//            System.out.println(i + Signs.ROCK[i]);
//        }


        int size = 3000;
//        Map map = new Map(size);
        Map map = new Map(10, 3);
//        Cell startCell = new Cell(size - 1, size - 1);
        Cell startCell = new Cell(0, 0);

        try (var writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            writer.write(RenderService.renderMap(map));
            writer.flush();

            Object[] resultPair = APathFindService.findNearestTargetStreamAPI(Hervibore.class, startCell, map);
            Hervibore hervibore = (Hervibore) resultPair[0];
            Cell herviCell = (Cell) resultPair[1];
            logger.debug(String.format("Hervibore: %s, cell: %s", hervibore, herviCell));

            Object[] resultPair2 = APathFindService.findNearestTargetStreamAPI(Grass.class, herviCell, map);
            Grass grass = (Grass) resultPair2[0];
            Cell grassCell = (Cell) resultPair2[1];
            logger.debug(String.format("Grass: %s, cell: %s", grass, grassCell));

            logger.debug("Path:" + AStarPathFindService.findPath(herviCell, grassCell, map).toString());

//            EntityFactory entityFactory = new EntityFactory();
//            logger.debug(entityFactory.getTypesStats(map.getEntitiesMap().values().stream().toList()).toString());


            writer.flush();



            while (true){
                var copyMap = new HashMap<>(map.getEntitiesMap());

                for (var entry : copyMap.entrySet()) {
                    if (entry.getValue() == null){
                        continue;
                    }
                    if (!entry.getValue().isAlive()){
                        logger.debug(entry.getValue());
                        map.getEntitiesMap().put(entry.getKey(), null);
                    }
                }
                String curState = RenderService.renderMap(map);
                hervibore.makeMove(map);
                String newState = RenderService.renderMap(map);
                writer.write("\n\n");
                writer.write(newState);
                writer.flush();
            }











//            var obstaclesMap = map.getObstaclesMap();
//            System.out.println("Obstacles:");
//            for (Cell cell : obstaclesMap.keySet()) {
//                System.out.println(cell);
//            }

//            var list = APathFindService.getAdjacentCells(startCell, map );
//            for (Cell cell : list) {
//                System.out.println(cell);
//            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        }

    }
