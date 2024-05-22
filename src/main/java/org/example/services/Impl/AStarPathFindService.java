package org.example.services.Impl;

import org.example.Cell;
import org.example.Map;
import org.example.services.APathFindService;

import java.util.*;
import java.util.stream.Collectors;

public class AStarPathFindService extends APathFindService {


    public static ArrayDeque<Cell> findPath(Cell startCell, Cell targetCell, Map map) {

        ArrayList<Node> nodeList = aStar(new Node(startCell), new Node(targetCell), map);
        if (nodeList != null && !nodeList.isEmpty()) {
            Collections.reverse(nodeList);
            ArrayDeque<Cell> path = nodeList.stream().map(n -> new Cell(n.x, n.y)).collect(Collectors.toCollection(ArrayDeque::new));
            path.removeFirst();
//            path.removeLast();
            return path;

        } else {
            return null;
        }

    }


    public static ArrayList<Node> aStar(Node startNode, Node endNode, Map map) {

        PriorityQueue<Node> openNodesQueue = new PriorityQueue<>();
        openNodesQueue.add(startNode);

        Set<Node> closedNodesSet = new HashSet<>();

        while (!openNodesQueue.isEmpty()) {
            Node currentNode = openNodesQueue.poll(); // вытаскиваем очередной узел

            // если текущий узел - искомый конечный узел, возвращаем путь из узлов,
            // включая начальный и конечный узел
            if (currentNode.equals(endNode)) {
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
                double new_g = currentNode.g + neighbour.calcEuclideanDistance(endNode);

                // если очередь с приоритетом (открытый список) уже содержит этот узел (этого соседа текущего узла)
                if (openNodesQueue.contains(neighbour)) {
                    // обновляем стоимости и родителя для этого узла (этого соседа текущего узла),
                    // если расстояние от начальной точки до этого узла (этого соседа текущего узла) меньше при
                    // прохождении через текущий узел, чем исходное (имеющееся у данного соседа текущего узла)
                    ArrayList<Node> nodeArrayList = new ArrayList<>(openNodesQueue);
                    Node foundNeighbour = nodeArrayList.get(nodeArrayList.indexOf(neighbour));
                    if (new_g < foundNeighbour.g) {
                        foundNeighbour.g = new_g;
                        foundNeighbour.h = neighbour.calcEuclideanDistance(endNode);
                        foundNeighbour.f = foundNeighbour.g + foundNeighbour.h;
                        foundNeighbour.parent = currentNode;
                    }
                }
                // если не содержит, то добавляем в неё с вычисленными расстояниями
                else {
                    neighbour.g = new_g;
                    neighbour.h = neighbour.calcEuclideanDistance(endNode);
                    neighbour.f = neighbour.g + neighbour.h;
                    neighbour.parent = currentNode;
                    openNodesQueue.add(neighbour);
                }
            }

        }

        return null;
    }


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
        public double calcEuclideanDistance(Node targetNode) {
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
