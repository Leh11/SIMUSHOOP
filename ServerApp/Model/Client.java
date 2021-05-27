package ServerApp.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class Client{
    
    private String name;
    private String email;
    private String password;

    public Client(String name, String email, String password){

        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public abstract void inicar_sessao(DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception;

}
