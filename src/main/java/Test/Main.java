package Test;

import network.graph.Graph;
import loadBalancer.LoadBalancer;
import network.Network;
import utils.FileUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String index = FileUtils.getFileName();
        Graph graph =  Network.createNetoworkFromFile("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/network_"+index+".json");
        LoadBalancer loadBalancer = new LoadBalancer(8080, graph, true);
        loadBalancer.start();
        //fazer comparação do tempo de resposta médio sem o minMax
    }
}
