package algoritms.testes;

import Graph.Edge;

import java.util.*;

public class GraphMaxFlow {
    int n;
    List<Edge>[] adj;

    public GraphMaxFlow(int n) {
        this.n = n;
        adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
    }


}
