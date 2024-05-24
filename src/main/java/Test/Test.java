package Test;

import Graph.ServerNode;
import Graph.Graph;
import Graph.*;
import algoritms.Algoritms;
import org.javatuples.Pair;

import static algoritms.Algoritms.minCostMaxFlowPair;

public class Test {
    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
    }

    static void testCase1() {
        String source = "A";
        String sink = "T";
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

        graph.nodes.forEach(node -> {
                    System.out.println("ARESTAS ADJACEENTES DE: " + node.name);
                    graph.adj_edges.get(node.name).forEach(
                            edge -> {}//{System.out.println("- " + edge.to);}
                    );
                }
        );

        graph.nodes.forEach(node -> {
                  //  System.out.println("NOS ADJACEENTES DE: " + node.name);
                    graph.adjacencyNodes.get(node.name).forEach(
                            NODE -> {}//{System.out.println("- " + NODE.name);}
                    );
                }
        );

        Pair<Integer, Integer> result = Algoritms.minCostMaxFlowPair(graph, source , sink);
        System.out.println("Test Case 1: " + result);
        //assert result.getValue0() == 23 : "Expected max flow 23";
        //assert result.getValue1() == 77 : "Expected min cost 77";
    }


    static void testCase2() {
       /* Graph graph = new Graph();
        graph.addEdge("A", "B", 10, 5);
        graph.addEdge("A", "C", 10, 6);
        graph.addEdge("B", "C", 5, 2);
        graph.addEdge("B", "D", 10, 4);
        graph.addEdge("C", "D", 10, 1);
        graph.addEdge("D", "T", 15, 2);

        Pair<Integer, Integer> result = minCostMaxFlowPair(graph, "A", "T");
        System.out.println("Test Case 2: " + result);
        assert result.getKey() == 15 : "Expected max flow 15";
        assert result.getValue() == 65 : "Expected min cost 65";*/
    }

    static void testCase3() {
       /* Graph graph = new Graph();
        graph.addEdge("S", "A", 8, 1);
        graph.addEdge("S", "B", 5, 2);
        graph.addEdge("A", "B", 3, 2);
        graph.addEdge("A", "C", 4, 3);
        graph.addEdge("B", "C", 5, 1);
        graph.addEdge("B", "D", 2, 3);
        graph.addEdge("C", "T", 6, 2);
        graph.addEdge("D", "T", 4, 1);

        Pair<Integer, Integer> result = minCostMaxFlowPair(graph, "S", "T");
        System.out.println("Test Case 3: " + result);
        assert result.getKey() == 10 : "Expected max flow 10";
        assert result.getValue() == 34 : "Expected min cost 34";*/
    }
}
