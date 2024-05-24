package org.example.services.Impl;

import org.example.entities.Cell;
import org.example.entities.Map;
import org.example.services.APathFindService;

import java.util.*;
import java.util.stream.Collectors;

public class AStarPathFindService extends APathFindService {


    /**
     * Находит кратчайший путь от заданного начального узла к целевому узлу с использованием алгоритма A*.
     *
     * @param  start  начальная ячейка
     * @param  target целевая ячейка
     * @param  map        карта, содержащая ячейки и препятствия
     * @return            кратчайший путь в виде ArrayDeque ячеек, или null, если путь не найден
     */
    public static ArrayDeque<Cell> findPath(Cell start, Cell target, Map map) {

        ArrayList<Node> nodeList = aStar(new Node(start), new Node(target), map);
        if (nodeList == null) {
            return null;
        }

        ArrayDeque<Cell> path = nodeList.stream().map(n -> new Cell(n.x, n.y)).collect(Collectors.toCollection(ArrayDeque::new));
        path.removeFirst();
//        path.removeLast();
        return path;


    }

    /**
     * CODEIUM
     * Находит кратчайший путь от заданного начального узла к целевому узлу с использованием алгоритма A*.
     *
     * @param  start  начальный узел
     * @param  end    целевой узел
     * @param  map    карта, содержащая узлы и препятствия
     * @return        кратчайший путь в виде ArrayList узлов, или null, если путь не найден
     */
    public static ArrayList<Node> aStar(Node start, Node end, Map map) {

        PriorityQueue<Node> openNodesQueue = new PriorityQueue<>();
        openNodesQueue.add(start);

        Set<Node> closedNodesSet = new HashSet<>();

        while (!openNodesQueue.isEmpty()) {
            Node currentNode = openNodesQueue.poll(); // вытаскиваем очередной узел

            // если текущий узел - искомый конечный узел, возвращаем путь из узлов,
            // включая начальный и конечный узел
            if (currentNode.equals(end)) {
                ArrayList<Node> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(currentNode);
                    currentNode = currentNode.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedNodesSet.add(currentNode); // пометили узел как обработанный

            ArrayList<Node> neighbours = (ArrayList<Node>) findValidNeighbours(currentNode, map); // ищем валидных соседей

            // теперь проходимся по всем соседям текущего узла
            for (Node neighbour : neighbours) {
                // проверяем, не обработали ли мы этот узел (этого соседа текущего узла) раньше
                if (closedNodesSet.contains(neighbour)) {
                    continue;
                } //TODO: можно вынести эту проверку findNeighbours

                // если нет, то вычисляем расстояние (в узлах) от стартовой точки до этого соседа через текущий узел
                // т.е. сумма последовательная сумма расстояний между узлами (диагональ - дороже)
                double new_g = currentNode.g + neighbour.calcDistance(end);

                // если очередь с приоритетом (открытый список) уже содержит этот узел (этого соседа текущего узла)
                if (openNodesQueue.contains(neighbour)) {
                    // обновляем стоимости и родителя для этого узла (этого соседа текущего узла),
                    // если расстояние от начальной точки до этого узла (этого соседа текущего узла) меньше при
                    // прохождении через текущий узел, чем исходное (имеющееся у данного соседа текущего узла)
                    ArrayList<Node> nodeArrayList = new ArrayList<>(openNodesQueue);
                    Node foundNeighbour = nodeArrayList.get(nodeArrayList.indexOf(neighbour));
                    if (new_g < foundNeighbour.g) {
                        foundNeighbour.g = new_g;
                        foundNeighbour.h = neighbour.calcDistance(end);
                        foundNeighbour.f = foundNeighbour.g + foundNeighbour.h;
                        foundNeighbour.parent = currentNode;
                    }
                }
                // если не содержит, то добавляем в неё с вычисленными расстояниями
                else {
                    neighbour.g = new_g;
                    neighbour.h = neighbour.calcDistance(end);
                    neighbour.f = neighbour.g + neighbour.h;
                    neighbour.parent = currentNode;
                    openNodesQueue.add(neighbour);
                }
            }

        }

        return null;
    }


    /**
     * CODEIUM
     * Находит допустимые соседние узлы заданного узла на карте, исключая любые ячейки, которые являются препятствиями.
     *
     * @param  node  узел, для которого нужно найти допустимые соседние узлы
     * @param  map   карта, содержащая узлы и препятствия
     * @return       список допустимых соседних узлов
     */
    private static List<Node> findValidNeighbours(Node node, Map map) {
        var obstaclesMap = map.getObstaclesMap();
        List<Cell> neighbourCells = getAdjacentCells(new Cell(node.x, node.y), map);

        return neighbourCells.stream()
                .filter(x -> !obstaclesMap.containsKey(x))
                .map(x -> new Node(x))
                .collect(Collectors.toList());
    }


    public static class Node implements Comparable<Node> {
        private final int x;            // координаты
        private final int y;
        private double g = 0;           // расстояние от начального узла
        private double h = 0;           // примерное расстояние до конечного узла
        private double f = 0;           // суммарная оценка стоимости
        private Node parent = null;     // родитель/источник для данного узла

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(Cell cell) {
            this.x = cell.getX();
            this.y = cell.getY();
        }

        // вычисляем Евклидово расстояние
        public double calcDistance(Node targetNode) {
            return Math.sqrt(Math.pow((targetNode.x - this.x), 2) + Math.pow((targetNode.y - this.y), 2));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[]{x, y});
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node node) {
                return (this.x == node.x && this.y == node.y);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", g=" + g +
                    ", h=" + h +
                    ", f=" + f +
                    '}';
        }

        // переопредилил compareTo, дабы в priorityQueue узлы автоматически
        // сортировались по суммарной оценке стоимости
        @Override
        public int compareTo(Node o) {
            return Double.compare(this.f, o.f);
        }
    }


}
