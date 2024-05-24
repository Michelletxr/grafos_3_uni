package Grafh_.Graph;

import org.javatuples.Pair;

public class Test {
    public static void main(String[] args) {
        testCase1();
       // testCase2();
        // testCase3();
    }

    static void testCase1() {
        Algoritms algoritms = new Algoritms();
        Graph graph = new Graph();
        graph.addEdge(new ServerNode("localhost", 0, "A"), new ServerNode("localhost", 0, "B"), 16, 2);
        graph.addEdge(new ServerNode("localhost", 0, "A"), new ServerNode("localhost", 0, "C"), 13, 1);
        graph.addEdge(new ServerNode("localhost", 0, "B"), new ServerNode("localhost", 0, "C"), 10, 1);
        graph.addEdge(new ServerNode("localhost", 0, "B"), new ServerNode("localhost", 0, "D"), 12, 3);
        graph.addEdge(new ServerNode("localhost", 0, "C"), new ServerNode("localhost", 0, "B"), 4, 1);
        graph.addEdge(new ServerNode("localhost", 0, "C"), new ServerNode("localhost", 0, "E"), 14, 2);
        graph.addEdge(new ServerNode("localhost", 0, "D"), new ServerNode("localhost", 0, "C"), 9, 1);
        graph.addEdge(new ServerNode("localhost", 0, "D"), new ServerNode("localhost", 0, "T"), 20, 1);
        graph.addEdge(new ServerNode("localhost", 0, "E"), new ServerNode("localhost", 0, "D"), 7, 1);
        graph.addEdge(new ServerNode("localhost", 0, "E"), new ServerNode("localhost", 0, "T"), 4, 3);


        Pair<Integer, Integer> result = Algoritms.minCostMaxFlowPair(graph, new ServerNode("localhost", 0, "A"), new ServerNode("localhost", 0, "T"));
        System.out.println("Test Case 1: " + result);
        assert result.getValue0() == 23 : "Expected max flow 23";
        assert result.getValue1() == 77 : "Expected min cost 77";
    }
}
