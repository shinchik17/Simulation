package org.example.algotries;

import java.util.*;

public class AStarAlgorithm {

    public static void main(String[] args) {

        int mapHeight = 5;
        int mapWeight = 7;



        Node startNode = new Node(1, 2);
        Node endNode = new Node(5, 2);
        boolean[][] obstacles = new boolean[mapHeight][mapWeight];
        obstacles[1][2] = true;
        obstacles[1][3] = true;
        obstacles[2][3] = true;



    }

    public static ArrayList<Node> aStar(Node startNode, Node endNode, boolean[][] obstacles){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(startNode);

        Set<Node> closedNodesSet = new HashSet<>();

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            if (currentNode.equals(endNode)) {
                ArrayList<Node> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(currentNode.parent);
                    currentNode = currentNode.parent;
                }
                return path;
            }

            closedNodesSet.add(currentNode);

            ArrayList<Node> neighbors = new ArrayList<>();
            for(int dx = -1; dx < 2; dx++){
                for (int dy = -1; dy < 2; dy++) {

                    if (dx == 0 && dy == 0){
                        continue;
                    }

                    int x = currentNode.x + dx;
                    int y = currentNode.y + dy;


                    // проверяем, не вышли ли мы за карту
                    if (x>= obstacles.length || x < 0 || y >= obstacles[0].length || y < 0){
                        continue;
                    }

                    // проверяем наличие препятствий в этом узле
                    if (obstacles[x][y]){
                        continue;
                    }

                    Node neighbor = new Node(x, y);
                    neighbors.add(neighbor);
                }

            }


            for(Node neighbor : neighbors){
                if (closedNodesSet.contains(neighbor)){
                    continue;
                }

                int new_g = currentNode.g + 1;


                neighbor.setH( + );
                neighbor.setG(neighbor.h);







            }




        }



        return null;
    }



    public static class Node implements Comparable<Node>{
        private int x;
        private int y;
        private int g = 0;
        private int h = 0;
        private int f = 0;
        private Node parent= null;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public int getF() {
            return f;
        }

        public void setF(int f) {
            this.f = f;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int[] getXY(){
            return new int[]{this.x, this.y};
        }

        public void setParent(Node parent) {
            this.parent = parent;
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

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.f, o.f);
        }
    }


}
