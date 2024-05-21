package org.example.services;

import org.example.Cell;
import org.example.Map;
import org.example.entities.Entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public interface PathFindService {

    //---Directions---//
    //  0     1     2
    //
    //  7     X     3
    //
    //  6     5     4
    //----------------//


    boolean isTargetExists(Entity target, Cell targetCell, Map map);

    ArrayDeque<Cell> findPath(Cell targetCell, Cell startCell, Map map);


}
