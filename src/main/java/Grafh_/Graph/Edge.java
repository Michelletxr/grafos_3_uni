package Grafh_.Graph;

class Edge {
    ServerNode from, to;
    int capacity, cost, flow;

    public Edge(ServerNode from, ServerNode to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = 0;
    }

    int residualCapacity() {
        return capacity - flow;
    }

    void updateFlow(int flow) {
        this.flow += flow;
        // Assume-se que a aresta reversa existe e está sendo atualizada em outro lugar
    }
}



