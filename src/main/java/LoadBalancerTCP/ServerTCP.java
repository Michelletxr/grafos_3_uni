package LoadBalancerTCP;

import java.io.*;

public abstract class ServerTCP {
    public String  host;
    public int port;

    public abstract  void startServer() throws IOException, ClassNotFoundException;
    public abstract void stopServer() throws IOException;

    public String receivePackage(InputStream input){
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String msg = null;
        try{
            msg = in.readLine();

        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("receive "+ msg);
        return msg;
    }

    public void sendPackage(String str, OutputStream output){
        PrintWriter out = new PrintWriter(output, true);
        System.out.println("send : " + str);
        out.println(str);
        out.flush();
    }
}
