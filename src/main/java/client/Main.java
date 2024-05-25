package client;

import loadBalancerTCP.LoadBalancer;

public class Main {
    public static void main(String[] args) {
        LoadBalancer loadBalancer = new LoadBalancer(8080);
        try{
            loadBalancer.startServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}