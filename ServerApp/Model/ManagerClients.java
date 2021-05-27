package ServerApp.Model;

import java.util.ArrayList;

public class ManagerClients {
    
    private ArrayList<Client> listOfClients;
    
    
    public ManagerClients(){
        this.listOfClients = new ArrayList<Client>();
    }

    public ArrayList<Client> getListOfClients() {
        return listOfClients;
    }

    public void addSaller(String name, String email, String password){
        this.listOfClients.add(new Saller(name, email, password));
    }
    public void addConsumer(String name, String email, String password){
        this.listOfClients.add(new Consumer(name, email, password));
    }
    public void addClient(Client client){
        this.listOfClients.add(client);
    }

    public Client findClient(String email, String password){
         
        for (Client client : listOfClients) {
            if(client.getEmail().equals(email) && client.getPassword().equals(password)){
                return client;
            }
         }
         return null;
    }

    public ArrayList<Product> buscar(String name){

        ArrayList<Product> listproducts = new ArrayList<Product>();
        for(Client client : listOfClients) {
            if(client instanceof Saller){
                Saller clientSaller = (Saller)client;
                for (Product product : clientSaller.getProducts()) {
                    if(product.getName().equalsIgnoreCase(name)){
                        listproducts.add(product);
                    }
                }
            }
        }    
        return listproducts;
    }

    public String listOfProducts(){

        String list = "";
        for (Client client : listOfClients) {
            if(client instanceof Saller){
                Saller sallerClient = (Saller)client;
                if(sallerClient.numberOfProducts() > 0){
                    list += listOfClients.indexOf(client) + " - Produtos de " + client.getName() + ":\n";
                    for (Product product : sallerClient.getProducts()) {
                        if(product.getAmount() > 0){
                            list += "-- " + product.getName() + " | RS: " + product.getUnitary_value() + " | Amount: " + product.getAmount() + "\n"; 
                        }
                    }
                }        
            }
        }
        return list;
    }
}
