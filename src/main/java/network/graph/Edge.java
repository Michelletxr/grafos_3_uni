package network.graph;

public class Edge {
    // no origem
    public Integer from;
    // no destino
    public Integer to;

    // capacidade da aresta
    public int capacity;
    // custo para usar a aresta
    public int cost;
    // fluxo atual
    public int flow;
    //aresta inversa
    public Edge reverse_edge;

    public Edge(int from, int to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = 0;
    }

    public int getCapacity() {
        return capacity - flow;
    }

    public void update_flow(int value) {
        this.flow += value;
       // this.reverse_edge.flow -= amount_value;
    }
}


