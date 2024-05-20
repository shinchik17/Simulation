package org.example.services.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Cell;
import org.example.Map;
import org.example.services.APathFindService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BreadthFirstPathFindService extends APathFindService {
    public static final Logger logger = LogManager.getLogger();
//
//    @Override
//    public ArrayDeque<Cell> findPath(Cell targetCell, Cell startCell, Map map) {
//
//        HashMap<String, ArrayList<String>> map = new HashMap<>();
//        map.put("you", new ArrayList<>(List.of("Alice", "Bob", "Claire")));
//        map.put("Bob", new ArrayList<>(List.of("Anuj", "Peggy")));
//        map.put("Alice", new ArrayList<>(List.of("Peggy")));
//        map.put("Claire", new ArrayList<>(List.of("Tom", "Johnny")));
//
//
//        logger.debug("KKKKKKKKKKKKKKKKKKKKKK");
//
//        ArrayDeque<String> queue = new ArrayDeque<>(map.get("you"));
//        ArrayList<String> searched = new ArrayList<>();
//
//        while (!queue.isEmpty()) {
//            String person = queue.pop();
//            System.out.println("Person " + person);
//            System.out.println("queue " + queue.toString());
//
//            if (!searched.contains(person)) {
//                if (isTarget(person)) {
//                    System.out.println("Found, it's " + person);
//                    break;
//                } else {
//                    searched.add(person);
//                    if (map.get(person) != null) {
//                        queue.addAll(map.get(person));
//                    }
//                }
//            }
//        }
//
//        return super.findPath(targetCell, startCell, map);
//    }
//

}
