package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.SystemMillisClock;
import org.example.entities.Creature;
import org.example.entities.Map;
import org.example.services.RenderService;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger();


    public static void main(String[] args) {


        try (var writer = new BufferedWriter(new OutputStreamWriter(System.out));
             var scanner = new Scanner(System.in))
        {

            Map map = new Map(10, 3);
            Simulation simulation = new Simulation(map, writer, 1);
            simulation.startSimulation();


            if (scanner.nextLine() != null) {
                simulation.pauseSimulation();
            }

//            Object[] resultPair = APathFindService.findNearestTargetStreamAPI(Hervibore.class, startCell, map);
//            Hervibore hervibore = (Hervibore) resultPair[0];
//            Cell herviCell = (Cell) resultPair[1];
//            logger.debug(String.format("Hervibore: %s, cell: %s", hervibore, herviCell));
//
//            Object[] resultPair2 = APathFindService.findNearestTargetStreamAPI(Grass.class, herviCell, map);
//            Grass grass = (Grass) resultPair2[0];
//            Cell grassCell = (Cell) resultPair2[1];
//            logger.debug(String.format("Grass: %s, cell: %s", grass, grassCell));
//            logger.debug("Path:" + AStarPathFindService.findPath(herviCell, grassCell, map).toString())

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
