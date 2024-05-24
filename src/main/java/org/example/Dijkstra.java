package org.example;

import java.util.*;

public class Dijkstra {
    static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjacencyList;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new LinkedList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new LinkedList<>();
            }
        }

        void addEdge(int source, int target, int weight) {
            Edge edge = new Edge(target, weight);
            adjacencyList[source].addFirst(edge);
        }

        void dijkstra(int startVertex) {
            boolean[] visited = new boolean[vertices];
            int[] distance = new int[vertices];
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertices, Comparator.comparingInt(e -> e.weight));

            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[startVertex] = 0;
            priorityQueue.add(new Edge(startVertex, 0));

            while (!priorityQueue.isEmpty()) {
                int u = priorityQueue.poll().target;

                if (!visited[u]) {
                    visited[u] = true;

                    for (Edge edge : adjacencyList[u]) {
                        int v = edge.target;
                        int weight = edge.weight;

                        if (!visited[v] && distance[u] + weight < distance[v]) {
                            distance[v] = distance[u] + weight;
                            priorityQueue.add(new Edge(v, distance[v]));
                        }
                    }
                }
            }

            printDijkstra(distance, startVertex);
        }

        void printDijkstra(int[] distance, int startVertex) {
            System.out.println("Caminho mais curto de " + startVertex + " para todos os vértices:");
            for (int i = 0; i < distance.length; i++) {
                System.out.println("Para vértice " + i + " a distância é " + distance[i]);
            }
        }
    }

    public static void main(String[] args) {
        int vertices = 6;
        Graph graph = new Graph(vertices);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 6);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 4, 4);
        graph.addEdge(2, 5, 2);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 5, 3);

        graph.dijkstra(0);
    }
}

