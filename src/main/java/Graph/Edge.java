package Graph;

public class Edge {
    // no origem
    public String from;
    // no destino
    public String to;
    // capacidade da aresta
    public int capacity;
    // custo para usar a aresta
    public int cost;
    // fluxo atual
    public int flow;
    //aresta inversa
    public Edge reverse_edge;

    public Edge(String from, String to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = 0;
    }

    public int residualCapacity() {
        return capacity - flow;
    }

    public void update_flow(int amount_value) {
        this.flow += amount_value;
       // this.reverse_edge.flow -= amount_value;
    }
}


