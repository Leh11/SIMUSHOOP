package ServerApp.Model;

//import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
//import java.io.InputStreamReader;

public class ThreadDealClient implements Runnable {
    
    private Socket connectionSocket;
    private ManagerClients managerClients;

    public ThreadDealClient(Socket connectionSocket, ManagerClients managerClients2){
        this.connectionSocket = connectionSocket;
        this.managerClients = managerClients2;
    }

    @Override
    public void run() {
        
        //BufferedReader dataFromClient;
        DataInputStream dataFromClient;
        DataOutputStream datatoClient;

        try {

            //dataFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            dataFromClient = new DataInputStream(connectionSocket.getInputStream());
            datatoClient = new DataOutputStream(connectionSocket.getOutputStream());
            String escolha = "";
            while(!escolha.equalsIgnoreCase("sair")){

                datatoClient.writeUTF("OLA, BEM VINDO AO SIMUSHOOP\n-----------------------------\n     1 - JA TENHO CONTA\n     2 - CADASTRAR\nSAIR - PARA SAIR\nDIGITE: \n");
                escolha = dataFromClient.readUTF();
                if(escolha.equals("1")){
                    //datatoClient.writeUTF("in\n");
                    logar(dataFromClient, datatoClient, managerClients);
                    dataFromClient.readUTF();  
                }else if(escolha.equals("2")){
                    cadastrar(dataFromClient, datatoClient, managerClients);
                    dataFromClient.readUTF();         
                    //datatoClient.writeUTF("log up \n");
                }else if(!escolha.equalsIgnoreCase("sair")){
                    datatoClient.writeUTF("Opcao invalida!\n");
                    dataFromClient.readUTF();
                }else{
                    datatoClient.writeUTF("DESCONECTADO! ....\n");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void logar(DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception{

        dataToClient.writeUTF("DIGITE SEU E-MAIL: ");
        String email = dataFromClient.readUTF();
        dataToClient.writeUTF("DIGITE SUA SENHA: ");
        String password = dataFromClient.readUTF();
        Client client = managerClients.findClient(email, password);    
        if(client != null){
            client.inicar_sessao(dataFromClient, dataToClient, managerClients);
        }else{
            dataToClient.writeUTF("USUARIO NAO ENCONTRADO\nPRESSIONE ENTER PARA CONTINUAR");        
        }
    }
    public static void cadastrar(DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception{

        
        dataToClient.writeUTF("1- COMPRADOR OU 2 - VENDEDOR: ");
        String typeClient = dataFromClient.readUTF();
        dataToClient.writeUTF("DIGITE SEU NOME: ");
        String name = dataFromClient.readUTF();
        dataToClient.writeUTF("DIGITE O SEU EMAIL");
        String email = dataFromClient.readUTF();
        dataToClient.writeUTF("DIGITE SUA SENHA");
        String password = dataFromClient.readUTF();

        if(typeClient.equals("1")){

            Client new_client = new Consumer(name, email, password);
            managerClients.addClient(new_client);
            new_client.inicar_sessao(dataFromClient, dataToClient, managerClients);
        }else{
            Client new_client = new Saller(name, email, password);
            managerClients.addClient(new_client);
            new_client.inicar_sessao(dataFromClient, dataToClient, managerClients);
        }





    }
}
