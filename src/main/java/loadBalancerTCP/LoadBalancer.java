package loadBalancerTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LoadBalancer extends ServerTCP {

    private ServerSocket serverSocket;
    private List<Integer> servicesPorts;

   // private static final Graph graph = new Graph();

    public LoadBalancer(Integer port) {
        this.port = port;
        this.host = "localhost";

        servicesPorts = new ArrayList<>();
        servicesPorts.add(8083);
        servicesPorts.add(8084);
    }

    @Override
    public void startServer() throws IOException, ClassNotFoundException {

        this.serverSocket = new ServerSocket(this.port);
        System.out.println("iniciando serviço load balancer na porta:" + serverSocket.getLocalPort());

        while (true){
            Socket client = serverSocket.accept();
            new Thread(new ThreadClient(client, this)).start();
        }

    }

    @Override
    public void stopServer() throws IOException {

    }

    // public synchronized ServerTCP getNextServer() {//definir qual proximo no baseado no fluxo maximo}
}
