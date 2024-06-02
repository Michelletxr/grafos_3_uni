package Test;

import network.graph.node.ClientNode;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class MainClient {
    public  static boolean is_rb = true;
    public static void testClienA(){
        String file_name;
        if(is_rb) file_name = "client_A_rb"; else file_name =  "client_A";

        int total_requests = 0;
        List<Long> time_response = new ArrayList<>();
        ClientNode clientNode =  new ClientNode("A");

        while (true){
            long startTime = System.currentTimeMillis(); // Marca o início do tempo
            clientNode.start();
            long endTime = System.currentTimeMillis(); // Marca o fim do tempo
            long duration = endTime - startTime; // Calcula a duração
            time_response.add(duration*1000); // Adiciona a duração à lista
           if(clientNode.stopped){
               break;
           }
           total_requests++;
        }
        System.out.println("Total Requests: " + total_requests);
        System.out.println("Time Responses: " + time_response.toString());
        String dataToSave = "Total Requests: " + total_requests + " | Response Time: " + time_response.toString();
        FileUtils.saveToFile("src/main/resources/"+ file_name+".txt", dataToSave);

    }

    public static void testClienB(){

        String file_name;
        if(is_rb) file_name = "client_B_rb"; else file_name =  "client_B";

        int total_requests = 0;
        List<Long> time_response = new ArrayList<>();
        ClientNode clientNode =  new ClientNode("B");
        while (true){
            long startTime = System.currentTimeMillis(); // Marca o início do tempo
            clientNode.start();
            long endTime = System.currentTimeMillis(); // Marca o fim do tempo
            long duration = endTime - startTime; // Calcula a duração
            time_response.add(duration*1000); // Adiciona a duração à lista

            if(clientNode.stopped){
                break;
            }
            total_requests++;
        }
        System.out.println("Total Requests: " + total_requests);
        System.out.println("Time Responses: " + time_response);
        String dataToSave = "Total Requests: " + total_requests + " | Response Time: " + time_response.toString();
        FileUtils.saveToFile("src/main/resources/"+ file_name+".txt", dataToSave);
    }

    public static void testClienC(){

        String file_name;
        if(is_rb) file_name = "client_C_rb"; else file_name =  "client_C";

        int total_requests = 0;
        List<Long> time_response = new ArrayList<>();
        ClientNode clientNode =  new ClientNode("C");

        while (true){
            long startTime = System.currentTimeMillis(); // Marca o início do tempo
            clientNode.start();
            long endTime = System.currentTimeMillis(); // Marca o fim do tempo
            long duration = endTime - startTime; // Calcula a duração
            time_response.add(duration*1000); // Adiciona a duração à lista

            if(clientNode.stopped){
                break;
            }
            total_requests++;
        }
        System.out.println("Total Requests: " + total_requests);
        System.out.println("Time Responses: " + time_response);
        String dataToSave = "Total Requests: " + total_requests + " | Response Time: " + time_response.toString();
        FileUtils.saveToFile("src/main/resources/"+ file_name+".txt", dataToSave);
    }

    public static void main(String[] args) {
        testClienA();
        testClienB();
        testClienC();
    }

}
