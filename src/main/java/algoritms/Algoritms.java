package algoritms;

import graph.Edge;
import graph.Graph;
import org.javatuples.Pair;

import java.util.*;

public interface Algoritms {

    static boolean dijkstra(Graph graph, String source, String sink, Map<String, Integer> dist_min, Map<String, Edge> path) {
        PriorityQueue<Pair<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue1));

        dist_min.clear();
        path.clear();

        for (String node : graph.getNameServersNodes()) {
            dist_min.put(node, Integer.MAX_VALUE);
        }

        dist_min.put(source, 0);
        pq.add(new Pair<>(source, 0));

        while (!pq.isEmpty()) {
            Pair<String, Integer> current = pq.poll();
            //System.out.println(current);
            int cost = current.getValue1();
            String node_curr = current.getValue0();
            System.out.println("custo | node " + cost  + " | " + node_curr);
            //System.out.println(" dist " + dist.get(node_curr).);
            // verificar esse trecho
            if (cost > dist_min.get(node_curr)) continue;
            for (Edge e : graph.adj_edges.get(node_curr)) {
                //System.out.println(" capacity  " + e.residualCapacity());
                if (e.getCapacity() > 0) {
                    int cost_ = e.cost + dist_min.get(node_curr);
                    if (cost_ < dist_min.get(e.to)) {
                        dist_min.put(e.to, cost_);
                        path.put(e.to, e);
                        pq.add(new Pair<>(e.to, dist_min.get(e.to)));
                    }
                }
            }
        }
         /*Retorna true se foi encontrado um caminho de source a sink
         com capacidade positiva e custo mínimo. */
        return dist_min.get(sink) != Integer.MAX_VALUE;
    }



    static Pair<Integer, Integer> minCostMaxFlowPair(Graph graph, String source, String sink) {
        Integer maxFlow = 0;
        Integer minCost = 0;

        // Vetor de distâncias utilizado pelo algoritmo para as menores distâncias/custos de source até cada nó.
        Map<String, Integer> dist_min = new HashMap<>(graph.total_nodes);

        // armazena as arestas utilizadas para chegar a cada nó no caminho de menor custo.
        Vector<Edge> edges_min_cost = new Vector<>(graph.total_nodes);
        Map<String, Edge> path = new HashMap<String, Edge>();

        // Enquanto houver um caminho de aumento no grafo
        while (Algoritms.dijkstra(graph, source, sink, dist_min, path)) {
            int flow = Integer.MAX_VALUE;
            // Encontra a capacidade mínima ao longo do caminho de aumento
            for (Edge e = path.get(sink); e != null; e = path.get(e.from)) {
                flow = Math.min(flow, e.getCapacity());
            }
            // Aumenta o fluxo ao longo do caminho encontrado
            for (Edge e = path.get(sink); e != null; e = path.get(e.from)) {
                e.update_flow(flow);
                minCost += flow * e.cost;
            }
            maxFlow += flow;
        }
         System.out.println("Max Flow: " + maxFlow);
         System.out.println("Min Cost: " + minCost);
        return new Pair<>(maxFlow, minCost);
    }
}
