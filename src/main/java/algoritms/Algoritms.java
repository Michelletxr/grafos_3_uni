package algoritms;

import Graph.Edge;
import Graph.Graph;
import org.javatuples.Pair;

import java.util.*;

public interface Algoritms {

    static boolean dijkstra(Graph graph, String source, String sink, Map<String, Integer> dist, Map<String, Edge> prev) {

        PriorityQueue<Pair<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue1));

        dist.clear();
        prev.clear();

        for (String node : graph.getNameServersNodes()) {
            dist.put(node, Integer.MAX_VALUE);
        }

        dist.put(source, 0);
        pq.add(new Pair<>(source, 0));

        while (!pq.isEmpty()) {
            Pair<String, Integer> current = pq.poll();
            System.out.println(current);
            int cost = current.getValue1();
            String node_curr = current.getValue0();
            //System.out.println("custo | node " + cost  + " | " + node_curr);
            //System.out.println(" dist " + dist.get(node_curr).);
            // verificar esse trecho
            if (cost > dist.get(node_curr)) continue;
            for (Edge e : graph.adj_edges.get(node_curr)) {
                //System.out.println(" capacity  " + e.residualCapacity());
                if (e.residualCapacity() > 0) {
                    int cost_ = e.cost + dist.get(node_curr);
                    if (cost_ < dist.get(e.to)) {
                        dist.put(e.to, cost_);
                        prev.put(e.to, e);
                        pq.add(new Pair<>(e.to, dist.get(e.to)));
                    }
                }
            }
        }

         /*Retorna true se a distância do nó source ao nó sink não é infinita,
         ou seja, se foi encontrado um caminho de source a sink
         com capacidade residual positiva e custo mínimo. */
        return dist.get(sink) != Integer.MAX_VALUE;
    }



static Pair<Integer, Integer> minCostMaxFlowPair(Graph graph, String source, String sink) {
    Integer maxFlow = 0;
    Integer minCost = 0;

    // Vetor de distâncias utilizado pelo algoritmo para as menores distâncias/custos de source até cada nó.
    Map<String, Integer> dist = new HashMap<>(graph.total_nodes);

    // armazena as arestas utilizadas para chegar a cada nó no caminho de menor custo.
    Vector<Edge> edges_min_cost = new Vector<>(graph.total_nodes);

    Map<String, Edge> prev = new HashMap<String, Edge>();



    // Enquanto houver um caminho de aumento no grafo residual
    while (Algoritms.dijkstra(graph, source, sink, dist, prev)) {
        int flow = Integer.MAX_VALUE;

        // Encontra a capacidade mínima residual ao longo do caminho de aumento
        for (Edge e = prev.get(sink); e != null; e = prev.get(e.from)) {
            flow = Math.min(flow, e.residualCapacity());
        }

        // Aumenta o fluxo ao longo do caminho encontrado
        for (Edge e = prev.get(sink); e != null; e = prev.get(e.from)) {
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
