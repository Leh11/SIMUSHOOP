package ServerApp;

import ServerApp.Model.*;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception{

        //porta = 3264
        ServerSocket server = new ServerSocket(3264);

        ManagerClients managerClients = new ManagerClients();
        System.out.println("Server inicialized at port 3264");

        while(true){
            Socket connectionSocket = server.accept();
            //add object to constructor of instance new_thread
            Thread new_thread = new Thread(new ThreadDealClient(connectionSocket, managerClients));
            new_thread.start();
        }
        

    }
}
