package org.shin.services;

import org.shin.entities.Impl.Hervibore;
import org.shin.entities.Cell;
import org.shin.entities.Map;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class APathFindServiceTest {

    @Test
    void test() throws IOException {
        Map map = new Map(5,5);
        System.out.println(RenderService.renderMap(map));;
        Cell startCell = new Cell(0, 0);
        Object[] resultPair = APathFindService.findNearestTarget(Hervibore.class, startCell, map);
        if (resultPair == null) {
            return;}
        System.out.printf("Target: %s, distance from {%d,%d}: %.1f c.u.", resultPair[0], startCell.getX(),
                startCell.getY(), ((Cell)resultPair[1]).calcDistance(startCell));

//        Assertions.assertTrue(true);
    }

}
