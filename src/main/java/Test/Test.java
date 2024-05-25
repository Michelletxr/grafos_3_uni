package Test;

import graph.ServerNode;
import graph.Graph;
import algoritms.Algoritms;
import org.javatuples.Pair;

import static algoritms.Algoritms.minCostMaxFlowPair;

public class Test {
    public static void main(String[] args) {
       // testCase1();
       // testCase2();
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

        Pair<Integer, Integer> result = minCostMaxFlowPair(graph, source , sink);
        System.out.println("Test Case 1: " + result);
        assert result.getValue0() == 23 : "Expected max flow 23";
        assert result.getValue1() == 77 : "Expected min cost 77";
    }


    static void testCase2() {

        String source = "S";
        String sink = "T";
        Graph graph = new Graph();
        graph.addEdge(new ServerNode("localhost", 0, "S"), new ServerNode("localhost", 0, "A"), 3, 0);
        graph.addEdge(new ServerNode("localhost", 0, "S"), new ServerNode("localhost", 0, "B"), 2, 0);
        graph.addEdge(new ServerNode("localhost", 0, "A"), new ServerNode("localhost", 0, "C"), 3, 1);
        graph.addEdge(new ServerNode("localhost", 0, "B"), new ServerNode("localhost", 0, "D"), 2, 2);
        graph.addEdge(new ServerNode("localhost", 0, "D"), new ServerNode("localhost", 0, "T"), 3, 1);
        graph.addEdge(new ServerNode("localhost", 0, "C"), new ServerNode("localhost", 0, "T"), 2, 0);
        Pair<Integer, Integer> result = minCostMaxFlowPair(graph, source , sink);
        System.out.println("Test Case 2: " + result);
        assert result.getValue0() == 23 : "Expected max flow 23";
        assert result.getValue1() == 77 : "Expected min cost 77";


    }

    static void testCase3() {
        String source = "1";
        String sink = "4";
        Graph graph = new Graph();
        graph.addEdge(new ServerNode("localhost", 0, "1"), new ServerNode("localhost", 0, "2"), 16, 0);
        graph.addEdge(new ServerNode("localhost", 0, "2"), new ServerNode("localhost", 0, "3"), 12, 0);
        graph.addEdge(new ServerNode("localhost", 0, "3"), new ServerNode("localhost", 0, "4"), 20, 0);
        graph.addEdge(new ServerNode("localhost", 0, "1"), new ServerNode("localhost", 0, "5"), 13, 0);
        graph.addEdge(new ServerNode("localhost", 0, "5"), new ServerNode("localhost", 0, "2"), 4, 0);
        graph.addEdge(new ServerNode("localhost", 0, "5"), new ServerNode("localhost", 0, "6"), 14, 0);
        graph.addEdge(new ServerNode("localhost", 0, "3"), new ServerNode("localhost", 0, "5"), 9, 0);
        graph.addEdge(new ServerNode("localhost", 0, "6"), new ServerNode("localhost", 0, "3"), 7, 0);
        graph.addEdge(new ServerNode("localhost", 0, "6"), new ServerNode("localhost", 0, "4"), 4, 0);

        Pair<Integer, Integer> result = minCostMaxFlowPair(graph, "1", "4");
        System.out.println("Test Case 3: " + result);
    }


}
