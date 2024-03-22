package ru.academits.shagaev.graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 0, 0, 0},
                {1, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0}
        };

        traverseDisconnectedGraph(graph);
    }

    public static void breadthFirstTraversal(int[][] graph, int start, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[vertex][neighbor] == 1 && !visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
    }

    public static void depthFirstTraversal(int[][] graph, int start, boolean[] visited) {
        visited[start] = true;
        System.out.println(start);

        for (int neighbour = 0; neighbour < graph.length; neighbour++) {
            if ((graph[start][neighbour] == 1) && (!visited[neighbour])) {
                depthFirstTraversal(graph, neighbour, visited);
            }
        }
    }

    public static void traverseDisconnectedGraph(int[][] graph) {
        int verticesNumber = graph.length;
        boolean[] visited = new boolean[verticesNumber];

        for (int vertex = 0; vertex < verticesNumber; vertex++) {
            if (!visited[vertex]) {
                System.out.println("\nОбход в ширину начат с вершины " + vertex);
                breadthFirstTraversal(graph, vertex, visited);
                System.out.println("\nОбход в глубину начат с вершины " + vertex);
                depthFirstTraversal(graph, vertex, visited);
            }
        }
    }
}