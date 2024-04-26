package org.example.algotries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BFS {

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        map.put("you", new ArrayList<>(List.of("Alice", "Bob", "Claire")));
        map.put("Bob", new ArrayList<>(List.of("Anuj", "Peggy")));
        map.put("Alice", new ArrayList<>(List.of("Peggy")));
        map.put("Claire", new ArrayList<>(List.of("Tom", "Johnny")));

        Logger logger = LogManager.getLogger();
        logger.debug("KKKKKKKKKKKKKKKKKKKKKK");

        ArrayDeque<String> queue = new ArrayDeque<>(map.get("you"));
        ArrayList<String> searched = new ArrayList<>();

        while (!queue.isEmpty()) {
            String person = queue.pop();
            System.out.println("Person " + person);
            System.out.println("queue " + queue.toString());

            if (!searched.contains(person)) {
                if (isTarget(person)) {
                    System.out.println("Found, it's " + person);
                    break;
                } else {
                    searched.add(person);
                    if (map.get(person) != null) {
                        queue.addAll(map.get(person));
                    }
                }
            }
        }

    }


    public static boolean isTarget(String person){
        return person.endsWith("m");
    }

}
