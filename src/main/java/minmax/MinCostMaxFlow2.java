package minmax;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinCostMaxFlow2 {
    static final int INF = Integer.MAX_VALUE;
    static final int V = 6; // Número de vértices no grafo

    // Implementação do algoritmo de Bellman-Ford + fordFulrkson para encontrar o caminho de custo mínimo
    boolean bellmanFord(int graph[][], int cost[][], int s, int t, int parent[], int dist[]) {
        Arrays.fill(dist, INF);
        dist[s] = 0;
        boolean inQueue[] = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        inQueue[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            inQueue[u] = false;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] > 0 && dist[u] + cost[u][v] < dist[v]) {
                    dist[v] = dist[u] + cost[u][v];
                    parent[v] = u;
                    if (!inQueue[v]) {
                        queue.add(v);
                        inQueue[v] = true;
                    }
                }
            }
        }
        return dist[t] != INF;
    }

    // Função para retornar o fluxo máximo com o custo mínimo do grafo
    int[] minCostMaxFlow(int graph[][], int cost[][], int s, int t) {
        int max_flow = 0, min_cost = 0;
        int u, v;

        // Cria um grafo residual e preenche o grafo residual com as capacidades
        // originais do grafo dado como entrada na capacidade residual
        int rGraph[][] = new int[V][V];
        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // Este array é preenchido por Bellman-Ford e para armazenar o caminho
        int parent[] = new int[V];
        int dist[] = new int[V];

        // Aumenta o fluxo enquanto houver um caminho da fonte para o sumidouro
        while (bellmanFord(rGraph, cost, s, t, parent, dist)) {
            // Encontra a capacidade residual mínima das arestas ao longo do
            // caminho preenchido por Bellman-Ford
            int path_flow = INF;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // Atualiza as capacidades residuais das arestas e
            // arestas reversas ao longo do caminho
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
                min_cost += path_flow * cost[u][v];
            }

            // Adiciona o fluxo do caminho ao fluxo total
            max_flow += path_flow;
        }

        return new int[]{max_flow, min_cost};
    }

    public static void main(String[] args) {
        // Grafo de exemplo com nós numerados de 0 a 5
        int graph[][] = new int[][]{
                { 0, 5, 0,  0,  0, 0},// A
                { 0,  0, 2, 0, 0, 0},// B
                { 0,  0,  0,  3, 0,  6},// C
                { 0,  0,  0,  0,  7, 0 },// D
                { 0,  0,  0,  0,  0,  0 },// E
                { 0,  0,  0,  0,  5,  0 } // T
        };

        int cost[][] = {
                { 0, 1, 0,  0,  0, 0},// A
                { 0,  0, 4, 0, 0, 0},// B
                { 0,  0,  0,  2, 0,  1},// C
                { 0,  0,  0,  0,  3, 0 },// D
                { 0,  0,  0,  0,  0,  0 },// E
                { 0,  0,  0,  0,  2,  0 } // T
        };

        MinCostMaxFlow2 mcmf = new MinCostMaxFlow2();

        int[] result = mcmf.minCostMaxFlow(graph, cost, 0, 4);

        System.out.println("O fluxo máximo é " + result[0]);
        System.out.println("O custo mínimo é " + result[1]);
    }
}