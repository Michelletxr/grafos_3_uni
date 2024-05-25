package algoritms;

import graph.*;

import java.util.*;

public interface Utils {
    // Retorna verdadeiro se houver um caminho do vértice de origem 's' ao vértice de destino 't'
    static boolean bfs_path(Graph graph, String source, String sink, Map<String, String> path) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        path.put(source, null);

        while (!queue.isEmpty()) {
            String u = queue.poll();

            for (ServerNode v : graph.adjacencyNodes.get(graph.getServerNode(u).name)) {
                if (!visited.contains(v.name) && graph.getEdgeCapacity(u, v.name) > 0) {
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

    static void printPath(Map<String, Edge> prev_path, ServerNode sink) {
        List<String> path = new ArrayList<>();
        for (Edge e = prev_path.get(sink.name); e != null; e = prev_path.get(e.from)) {
            path.add(e.from);
        }
        Collections.reverse(path);
        path.add(sink.name);
        System.out.println("Caminho de aumento: " + String.join(" -> ", path));
    }

}
