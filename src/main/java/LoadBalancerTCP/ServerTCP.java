package LoadBalancerTCP;

import java.io.*;

public abstract class ServerTCP {

    public abstract  void startServer() throws IOException, ClassNotFoundException;
    public abstract void stopServer() throws IOException;

    public String receivePackage(InputStream input){
        String msg = null;
        try{
            ObjectInputStream in = new ObjectInputStream(input);
            msg = (String) in.readObject();
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("receive "+ msg);
        return msg;
    }

    public void sendPackage(String str, OutputStream output){
        try {
            System.out.println("send : " + str);
            ObjectOutputStream out = new ObjectOutputStream(output);
            out.writeObject(str);
            out.flush();
        }catch (IOException e){
            System.err.println("ocorreu um erro ao enviar mensagem server:" + e);
        }
    }
}
