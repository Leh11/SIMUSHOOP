package ClientApp;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    
    public static void main(String[] args) throws IOException {
        
        //
        Scanner input = new Scanner(System.in);

        //por default é usado o localhost
        //Substitua "localhost" pelo endereco do servidor
        Socket newConnection = new Socket("localhost", 3264);
        System.out.println("Client connected at port 3264\n-----------------------------");

        BufferedReader dataFromUser = new BufferedReader(new InputStreamReader(System.in));

        DataOutputStream dataToServer = new DataOutputStream(newConnection.getOutputStream());

        DataInputStream dataFromServer = new DataInputStream(newConnection.getInputStream());
        String option = "";
        
        while(!option.equalsIgnoreCase("sair")){

            String in = dataFromServer.readUTF();
            System.out.println(in);
            if(in.equalsIgnoreCase("QUANTO DESEJA ADICIONAR AO CASH: ") || in.equalsIgnoreCase("DIGITE O VALOR DO PRODUTO: ") ||
                in.equalsIgnoreCase("DIGITE A QUANTIDADE DISPONIVEL DESTE PRODUTO: ") ||
                in.equalsIgnoreCase("SELECIONE O PRODUTO: ") ||
                in.equalsIgnoreCase("Qual a quantidade do produto") ||
                in.equalsIgnoreCase("\n ESCOLHA O VENDEDOR:\n")){
                double value = input.nextDouble();
                input.nextLine();
                dataToServer.writeDouble(value);
            }else{
                option = dataFromUser.readLine();
                dataToServer.writeUTF(option);
            }
            
         
            
            //System.out.println(dataFromServer.readUTF());
            
        }
        //boolean on = true;
            
        
        //dataToServer.writeBytes(input.nextLine() + "\n");
        //System.out.println(dataFromServer.readLine());

            
            
        newConnection.close();

        
        input.close();
    }

   /*  public static void printout(Iterator inp){
        while(inp.hasNext()){
            System.out.println(inp.next());
        }
    } */

}