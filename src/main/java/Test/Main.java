package Test;

import network.graph.Graph;
import loadBalancer.LoadBalancer;
import network.Network;
import utils.FileUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String fileName = FileUtils.getFileName();
        //String fileName = "network_1";
        Graph graph =  Network.createNetoworkFromFile("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/"+fileName+".json");
        LoadBalancer loadBalancer = new LoadBalancer(8080, graph, false);
        loadBalancer.start();
        //fazer comparação do tempo de resposta médio sem o minMax
    }
}
