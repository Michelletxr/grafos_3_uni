package algoritms;

import Graph.*;

import java.util.*;

public interface Utils {
    static boolean bfs_cost(int source, int sink, int[] dist, Edge[] prev) {
        /*Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        boolean[] inQueue = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        inQueue[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            inQueue[u] = false;

            for (Edge e : adj[u]) {
                if (e.residualCapacity() > 0) {
                    int costWithPotential = e.cost + potential[u] - potential[e.to];
                    if (dist[u] + costWithPotential < dist[e.to]) {
                        dist[e.to] = dist[u] + costWithPotential;
                        prev[e.to] = e;
                        if (!inQueue[e.to]) {
                            queue.add(e.to);
                            inQueue[e.to] = true;
                        }
                    }
                }
            }
        }*/
        return true;
    }

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

    // Realiza uma busca em largura para construir o grafo de níveis
    static boolean bfs_level(Graph graph, String source, String sink, Map<String, Integer> level) {
        level.clear();
        Queue<String> queue = new LinkedList<>();
        queue.add(source);
        level.put(source, 0);

        // Enquanto a fila não estiver vazia, processe os nós
        while (!queue.isEmpty()) {
            String u = queue.poll();

            // Verifique todos os nós adjacentes
            for (ServerNode v : graph.adjacencyNodes.get(u)) {
                // Se o nó ainda não foi visitado e há capacidade na aresta
                if (!level.containsKey(v.name) && graph.getEdgeCapacity(u, v.name) > 0) {
                    // Atualize o nível do nó e adicione à fila
                    level.put(v.name, level.get(u) + 1);
                    queue.add(v.name);
                }
            }
        }

        // Retorna verdadeiro se o nó de destino foi alcançado
        return level.containsKey(sink);
    }



}
