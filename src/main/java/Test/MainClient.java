package Test;

import network.graph.node.ClientNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClient {
    public static void main(String[] args) {
        List<String> clientsNames = new ArrayList<>(Arrays.asList("A", "B", "C"));
        int valor = 0;
        while (valor < 100){
            clientsNames.forEach( name -> {
                new ClientNode(name).start();
            });
            valor=valor+1;
        }


    }
}
