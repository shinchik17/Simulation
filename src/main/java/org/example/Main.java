package org.example;

import org.example.entities.Impl.Hervibore;
import org.example.services.APathFindService;
import org.example.services.Impl.BreadthFirstPathFindService;
import org.example.services.RenderService;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

//        for (int i = 0; i < Signs.ROCK.length; i++) {
//            System.out.println(i + Signs.ROCK[i]);
//        }


        int size = 3000;
//        Map map = new Map(size);
        Map map = new Map(size, size);
        Cell startCell = new Cell(size - 1, size - 1);

        try (var writer = new PrintWriter(new OutputStreamWriter(System.out))) {
//            System.out.println(RenderService.renderMap(map));

            long t0 = System.currentTimeMillis();
            Object[] resultPair = APathFindService.findNearestTarget(Hervibore.class, startCell, map);
            long t1 = System.currentTimeMillis();
            Object[] resultPair2 = APathFindService.findNearestTargetStreamAPI(Hervibore.class, startCell, map);
            long t2 = System.currentTimeMillis();

            if (resultPair == null) {return;}
            if (resultPair2 == null) {return;}

            writer.printf("Target: %s, distance from {%d,%d}: %.1f c.u.\n", resultPair[0], startCell.getX(),
                    startCell.getY(), ((Cell)resultPair[1]).calcDistance(startCell));
            writer.printf("Target: %s, distance from {%d,%d}: %.1f c.u.\n", resultPair2[0], startCell.getX(),
                    startCell.getY(), ((Cell)resultPair2[1]).calcDistance(startCell));

            writer.printf("First method time: %.3f s\n", (t1-t0) / 1000f);
            writer.printf("Second method time: %.3f s\n", (t2-t1) / 1000f);


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
