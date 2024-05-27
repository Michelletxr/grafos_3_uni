package algoritms;

import graph.Edge;
import graph.Graph;
import graph.GraphV1;
import org.javatuples.Pair;

import java.util.*;

public interface Algoritms {

    static boolean dijkstra(GraphV1 graphV1, String source, String sink, Map<String, Integer> dist_min, Map<String, Edge> path) {
        PriorityQueue<Pair<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue1));

        dist_min.clear();
        path.clear();

        for (String node : graphV1.getNameServersNodes()) {
            dist_min.put(node, Integer.MAX_VALUE);
        }

        dist_min.put(source, 0);
        pq.add(new Pair<>(source, 0));

        while (!pq.isEmpty()) {
            Pair<String, Integer> current = pq.poll();
            //System.out.println(current);
            String node_curr = current.getValue0();
            int cost_curr = current.getValue1();
            System.out.println("custo | node " + cost_curr  + " | " + node_curr);

            /* verifica se o custo para chegar ao nó atual
             é maior que o custo já calculado para a distância até esse nó */
            if (cost_curr > dist_min.get(node_curr)) continue;

            // verifica as arestas adjacentes ao nó
            for (Edge e : graphV1.adj_edges.get(node_curr)) {
                //System.out.println(" capacity  " + e.residualCapacity());
                //verifica se a aresta possuo capacidade para aumento de fluxo
                if (e.getCapacity() > 0) {
                    //realiza o calculo para o costo do nó seguinte
                    // custo  para chegar até o nó atual + o custo da aresta que leva até o nó seguinte
                    int cost_ = e.cost + dist_min.get(node_curr);
                    //caso esse custo sejá menor que o custo já existente para chegar até o nó seguinte
                    if (cost_ < dist_min.get(e.to)) {
                        //atualiza o custo para chegar até o nó seguinte
                        dist_min.put(e.to, cost_);
                        //adiciona a aresta ao caminho de aumento
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



    static Pair<Integer, Integer> minCostMaxFlowPair(GraphV1 graphV1, String source, String sink) {
        Integer maxFlow = 0;
        Integer minCost = 0;

        // Vetor de distâncias utilizado pelo algoritmo para as menores distâncias/custos de source até cada nó.
        Map<String, Integer> dist_min = new HashMap<>(graphV1.total_nodes);

        // armazena as arestas utilizadas para chegar a cada nó no caminho de menor custo.
        Vector<Edge> edges_min_cost = new Vector<>(graphV1.total_nodes);
        Map<String, Edge> path = new HashMap<String, Edge>();

        // Enquanto houver um caminho de aumento no grafo
        while (Algoritms.dijkstra(graphV1, source, sink, dist_min, path)) {
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
    static boolean dijkstra(Graph graph, Integer source, Integer sink, Map<Integer, Integer> dist_min, Map<Integer, Edge> path) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue1));

        dist_min.clear();
        path.clear();

        for (Integer node : graph.getNodesKeys()) {
            dist_min.put(node, Integer.MAX_VALUE);
        }

        dist_min.put(source, 0);
        pq.add(new Pair<>(source, 0));

        while (!pq.isEmpty()) {
            Pair<Integer, Integer> current = pq.poll();
            //System.out.println(current);
            Integer node_curr = current.getValue0();
            int cost_curr = current.getValue1();
            System.out.println("custo | node " + cost_curr  + " | " + node_curr);

            /* verifica se o custo para chegar ao nó atual
             é maior que o custo já calculado para a distância até esse nó */
            if (cost_curr > dist_min.get(node_curr)) continue;

            // verifica as arestas adjacentes ao nó
            for (Edge e : graph.adj_edges.get(node_curr)) {
                //System.out.println(" capacity  " + e.residualCapacity());
                //verifica se a aresta possuo capacidade para aumento de fluxo
                if (e.getCapacity() > 0) {
                    //realiza o calculo para o costo do nó seguinte
                    // custo  para chegar até o nó atual + o custo da aresta que leva até o nó seguinte
                    int cost_ = e.cost + dist_min.get(node_curr);
                    //caso esse custo sejá menor que o custo já existente para chegar até o nó seguinte
                    if (cost_ < dist_min.get(e.to)) {
                        //atualiza o custo para chegar até o nó seguinte
                        dist_min.put(e.to, cost_);
                        //adiciona a aresta ao caminho de aumento
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
}
