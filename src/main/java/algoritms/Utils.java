package algoritms;

import graph.*;
import graph.node.Node;

import java.util.*;

public interface Utils {
    // Retorna verdadeiro se houver um caminho do vértice de origem 's' ao vértice de destino 't'
    static boolean bfs_path(GraphV1 graphV1, String source, String sink, Map<String, String> path) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        path.put(source, null);

        while (!queue.isEmpty()) {
            String u = queue.poll();

            for (ServerNode v : graphV1.adjacencyNodes.get(graphV1.getServerNode(u).name)) {
                if (!visited.contains(v.name) && graphV1.getEdgeCapacity(u, v.name) > 0) {
                    queue.add(v.name);
                    visited.add(v.name);
                    path.put(v.name, u);

                    if (v.name.equals(sink)) {
                        return true;
                    }
                }
            }
        }
        // Não alcançamos o destino na BFS começando da origem, então retorna falso
        return false;
    }

    static void printPath(Map<Integer, Edge> prev_path, Node sink) {
        List<Integer> path = new ArrayList<>();
        for (Edge e = prev_path.get(sink.id); e != null; e = prev_path.get(e.from)) {
            path.add(e.from);
        }
        Collections.reverse(path);
        path.add(sink.id);
        System.out.println("Caminho de aumento: " + String.join(" -> ", path.stream().toString()));
        //adaptar classes de algoritmos para nova estrutura
    }

}
