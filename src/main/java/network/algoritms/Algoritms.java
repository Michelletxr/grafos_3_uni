package network.algoritms;

import network.graph.Edge;
import network.graph.Graph;
import org.javatuples.Pair;

import java.util.*;

public interface Algoritms {

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
            Integer node_curr = current.getValue0();
            int cost_curr = current.getValue1();

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

    public static boolean bellmanFord(Graph graph, int cost[][], int s, int t, int parent[], int dist[]) {
        Arrays.fill(dist, Integer.MAX_VALUE);
        int V = graph.total_nodes;
        dist[s] = 0;

        boolean inQueue[] = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        inQueue[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            inQueue[u] = false;
            for (Edge e : graph.adj_edges.get(u)) {
                int v = e.to;
                int cost_e = e.cost + dist[u];
                if (e.getCapacity() > 0 && dist[u] + cost_e < dist[v]) {
                    dist[v] = dist[u] + cost_e;
                    parent[v] = u;
                    if (!inQueue[v]) {
                        queue.add(v);
                        inQueue[v] = true;
                    }
                }
            }
        }
        return dist[t] != Integer.MAX_VALUE;
    }


    public static int[] minCostMaxFlowWithBF(Graph graph, int cost[][], int s, int t) {
        int max_flow = 0, min_cost = 0;
        int u, v;
        Graph rGraph = graph;

        // Este array é preenchido por Bellman-Ford e para armazenar o caminho
        int V = rGraph.total_nodes;
        int parent[] = new int[V];
        int dist[] = new int[V];

        // Aumenta o fluxo enquanto houver um caminho da fonte para o sumidouro
        while (bellmanFord(rGraph, cost, s, t, parent, dist)) {
            // Encontra a capacidade residual mínima das arestas ao longo do
            // caminho preenchido por Bellman-Ford
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph.getEdge(u, v).capacity);
            }

            // Atualiza as capacidades residuais das arestas e
            // arestas reversas ao longo do caminho
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph.updateFlowEdge(u, v, path_flow);
                min_cost += path_flow * rGraph.getEdge(u,v).cost;
            }

            // Adiciona o fluxo do caminho ao fluxo total
            max_flow += path_flow;
        }

        return new int[]{max_flow, min_cost};
    }



    static Pair<Integer, Integer> minCostMaxFlowWithDJ(Graph graph, Integer source, Integer sink) {
        Integer maxFlow = 0;
        Integer minCost = 0;
        Graph rGraph = graph;

        // Vetor de distâncias utilizado pelo algoritmo para as menores distâncias/custos de source até cada nó.
        Map<Integer, Integer> dist_min = new HashMap<>(rGraph.total_nodes);

        // armazena as arestas utilizadas para chegar a cada nó no caminho de menor custo.
        Vector<Edge> edges_min_cost = new Vector<>(rGraph.total_nodes);
        Map<Integer, Edge> path = new HashMap<>();

        // Enquanto houver um caminho de aumento no grafo
        while (Algoritms.dijkstra(rGraph, source, sink, dist_min, path)) {
            int flow = Integer.MAX_VALUE;
            // Encontra a capacidade mínima ao longo do caminho de aumento
            for (Edge e = path.get(sink); e != null; e = path.get(e.from)) {
                flow = Math.min(flow, e.getCapacity());
                //System.out.println("flow: " + flow);
            }
            // Aumenta o fluxo ao longo do caminho encontrado
            for (Edge e = path.get(sink); e != null; e = path.get(e.from)) {
                rGraph.updateFlowEdge(e.from, e.to, flow);
                minCost += flow * e.cost;
            }
            maxFlow += flow;
        }
        System.out.println("Max Flow: " + maxFlow);
        System.out.println("Min Cost: " + minCost);
        return new Pair<>(maxFlow, minCost);
    }
}
