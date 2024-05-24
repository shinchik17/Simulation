package org.shin.services;

import org.shin.entities.Entity;
import org.shin.entities.Cell;
import org.shin.entities.Map;

import java.util.HashMap;

public class RenderService {


    public static String renderMap(Map map) {
        int width = map.getWidth();
        int height = map.getHeight();
        HashMap<Cell, Entity> entitiesMap = map.getEntitiesMap();
        StringBuilder mapPicture = new StringBuilder("\n");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var entity = entitiesMap.get(new Cell(x, y));
                if (entity != null && !entity.isAlive()){
                    entity = null;
                }
//              mapPicture.append(cell == null ? " .. " : " " + cell.getSign() + " ");
                mapPicture.append(entity == null ? "  |" : entity.getSign() + "|");
                mapPicture.append("\t");
            }

            mapPicture.append(System.lineSeparator());
            mapPicture.append("--\t".repeat(Math.max(0, width)));
            mapPicture.append(System.lineSeparator());
        }

//        targetWriter.write(mapPicture.toString());
        mapPicture.append("\n");
        return mapPicture.toString();


    }


}
