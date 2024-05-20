package org.example;

import org.example.services.APathFindService;
import org.example.services.Impl.BreadthFirstPathFindService;
import org.example.services.RenderService;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {

//        for (int i = 0; i < Signs.ROCK.length; i++) {
//            System.out.println(i + Signs.ROCK[i]);
//        }

        Map map = new Map(8, 4);
        Cell startCell = new Cell(4, 2);

        try (var writer = new PrintWriter(new OutputStreamWriter(System.out))) {
            System.out.println(RenderService.renderMap(map));


//            Object[] resultPair = APathFindService.findNearestTarget(Hervibore.class, startCell, map);
//            if (resultPair == null) {
//                return;}
//            writer.printf("Target: %s, distance from {%d,%d}: %.1f c.u.", resultPair[0], startCell.getX(),
//                    startCell.getY(), ((Cell)resultPair[1]).calcDistance(startCell));

//            var obstaclesMap = map.getObstaclesMap();
//            System.out.println("Obstacles:");
//            for (Cell cell : obstaclesMap.keySet()) {
//                System.out.println(cell);
//            }

            var list = APathFindService.getAdjacentCells(startCell, map );
            for (Cell cell : list) {
                System.out.println(cell);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        }

    }
