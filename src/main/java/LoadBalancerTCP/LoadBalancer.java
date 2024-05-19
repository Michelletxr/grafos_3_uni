package LoadBalancerTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class LoadBalancer extends ServerTCP {

    private ServerSocket serverSocket;
    private Integer port;
    private Integer databasePort;
    private Integer servicePort;
    private List<Integer> services;
    static boolean  jmeterLogged = false;

    public LoadBalancer(Integer port, Integer databasePort) {
        this.port = port;
        this.databasePort = databasePort;
        services = new ArrayList<>();
        //daoServers = new ArrayList<>();
        //daoServers.add(8081);
        //daoServers.add(8082);
        services.add(8083);
        services.add(8084);
    }

    @Override
    public void startServer() throws IOException, ClassNotFoundException {

    }

    @Override
    public void stopServer() throws IOException {

    }
}
