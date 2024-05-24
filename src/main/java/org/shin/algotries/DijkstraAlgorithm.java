package org.shin.algotries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraAlgorithm {

    public static void main(String[] args) {

        // описываем направленный граф
        Map<String, Map<String, Integer>> graphMap = new HashMap<>();
        graphMap.put("Start", Map.of("A", 6, "B", 2));
        graphMap.put("A", Map.of("End", 1));
        graphMap.put("B", Map.of("A", 3, "End", 5));
        graphMap.put("End", null);

        // прописываем стоимость достижения узлов (веса рёбер)
        Map<String, Integer> costsMap = new HashMap<>();
        costsMap.put("A", 6);
        costsMap.put("B", 2);
        costsMap.put("End", Integer.MAX_VALUE); // в идеале сделать float и Infinity, но лень)))

        // прописываем родителей для каждого узла
        Map<String, String> parentsMap = new HashMap<>();
        parentsMap.put("A", "Start");
        parentsMap.put("B", "Start");
        parentsMap.put("End", null);

        // лист обработанных узлов
        ArrayList<String> processedNodes = new ArrayList<>();

        System.out.println(costsMap);
        System.out.println(parentsMap);

        String node = find_lowest_cost_node(costsMap, processedNodes);  // находим узел с наименьшей стоимостью
        while (node != null) {                                          // перебираем все необработанные узлы
            int cost = costsMap.get(node);
            Map<String, Integer> neighbors = graphMap.get(node);


            /* перебираем всех соседей этого узла, обновляем их стоимости и родителей, если
            стоимость при прохождении через текущий узел меньше исходной   */
            if (neighbors != null) {
                for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
                    String neighborName = entry.getKey();
                    int new_cost = cost + entry.getValue();
                    if (new_cost < costsMap.get(neighborName)) {
                        costsMap.put(neighborName, new_cost);
                        parentsMap.put(neighborName, node);
                    }
                }
            }

            processedNodes.add(node);                                   // отмечаем обработанным
            node = find_lowest_cost_node(costsMap, processedNodes);     // ищем следующий необработанный
        }


        System.out.println(costsMap);
        System.out.println(parentsMap);
    }

    // поиск узла с наименьшей стоимостью достижения среди необработанных
    public static String find_lowest_cost_node(Map<String, Integer> costs, List<String> processedList) {

        int lowestCost = Integer.MAX_VALUE;
        String lowestCostNode = null;

        for (Map.Entry<String, Integer> entry : costs.entrySet()) {
            if (entry.getValue() < lowestCost && !processedList.contains(entry.getKey())) {
                lowestCost = entry.getValue();
                lowestCostNode = entry.getKey();
            }
        }

        return lowestCostNode;
    }


}
