package Grafh_.Graph;

import org.javatuples.Pair;

import java.util.*;

import java.util.PriorityQueue;

public class Algoritms {

    public static boolean dijkstra(Graph graph, ServerNode source, ServerNode sink, Map<String, Integer> dist, Map<String, Edge> prev) {
        PriorityQueue<Pair<String, Integer>> pr = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue1));
        dist.clear();
        prev.clear();
        for (ServerNode node : graph.nodes) {
            dist.put(node.name, Integer.MAX_VALUE);
        }
        dist.put(source.name, 0);
        pr.add(new Pair<>(source.name, 0));

        while (!pr.isEmpty()) {
            Pair<String, Integer> current = pr.poll();
            String u = current.getValue0();
            int d = current.getValue1();

            System.out.println(current.getValue0() + " chech " + current.getValue1() + " " + u);

            if (d > dist.get(u)) continue;

            for (Edge e : graph.adj.get(u)) {
                if (e.residualCapacity() > 0) {
                    int cost = e.cost + dist.get(u);
                    if (cost < dist.get(e.to.name)) {
                        dist.put(e.to.name, cost);
                        prev.put(e.to.name, e);
                        pr.add(new Pair<>(e.to.name, dist.get(e.to.name)));
                    }
                }
            }
        }
       // System.out.println(dist.get(sink.name));
        return dist.get(sink.name) != Integer.MAX_VALUE;
    }

    public static Pair<Integer, Integer> minCostMaxFlowPair(Graph graph, ServerNode source, ServerNode sink) {
        Integer maxFlow = 0;
        Integer minCost = 0;

        // Vetor de distâncias utilizado pelo algoritmo para as menores distâncias/custos de source até cada nó.
        Map<String, Integer> dist = new HashMap<>();

        // Armazena as arestas utilizadas para chegar a cada nó no caminho de menor custo.
        Map<String, Edge> prev = new HashMap<>();

        System.out.println("fluxo");

        graph.edges.forEach(n -> {
            System.out.println("fluxo" + n.flow);
        });

        // Enquanto houver um caminho de aumento no grafo residual
        while (Algoritms.dijkstra(graph, source, sink, dist, prev)) {
            int flow = Integer.MAX_VALUE;

            // Encontra a capacidade mínima residual ao longo do caminho de aumento
            for (Edge e = prev.get(sink.name); e != null; e = prev.get(e.from.name)) {
                flow = Math.min(flow, e.residualCapacity());
            }

            // Aumenta o fluxo ao longo do caminho encontrado
            for (Edge e = prev.get(sink.name); e != null; e = prev.get(e.from.name)) {
                e.updateFlow(flow);
                //minCost += e.cost;
            }
            //minCost =0;
            maxFlow += flow;
        }
        for (Edge e : graph.edges) {
            if(e.flow > 0 ){
                minCost += e.cost;
            }
        }


        System.out.println("Max Flow: " + maxFlow);
        System.out.println("Min Cost: " + minCost);
        return new Pair<>(maxFlow, minCost);
    }
}