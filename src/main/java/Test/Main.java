package Test;

import network.graph.Graph;
import loadBalancer.LoadBalancer;
import network.Network;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Graph graph =  Network.createNetoworkFromFile("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/network.json");
        LoadBalancer loadBalancer = new LoadBalancer(8080, graph);
        loadBalancer.start();
    }
}
