package ServerApp.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class Saller extends Client{

    private ArrayList<Product> products;

    public Saller(String name, String email, String password){
        super(name, email, password);
        this.products = new ArrayList<Product>();
    }

    public void addProduct(String name, double unitary_value, int amount){
        products.add(new Product(name, unitary_value, amount));
    }

    public int numberOfProducts(){
        return this.products.size();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public void inicar_sessao(DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception{
        
        String option = "";
        dataToClient.writeUTF("\nBEM VINDO " + this.getName() + " PRESSIONE ENTER PARA CONTINUAR");
        dataFromClient.readUTF();
        while(!option.equalsIgnoreCase("voltar")){
            
            dataToClient.writeUTF("1 - COLOCAR PRODUTO A VENDA\n2 - VER PRODUTOS VENDIDOS \n3 - VER VALOR GANHO\n4 - VER SEUS PRODUTOS A VENDA\n5 - PARA VOLTAR");
            option = dataFromClient.readUTF();

            switch (option) {
                case "1":
                    addProductToSale(dataFromClient, dataToClient);
                    break;
                case "2":
                    showProductsSold(dataFromClient, dataToClient);
                    break;
                case "3":
                    showEarnedMoney(dataFromClient, dataToClient);
                    break;
                case "4":
                    listOfProductsAtSale(dataFromClient, dataToClient);
                    break;
                case "5":
                    option = "VOLTAR";
                    dataToClient.writeUTF("VOLTANDO...\nPRESS ENTER PARA CONTINUAR");
                    break;
                default:
                    dataToClient.writeUTF("OPCAO INVALIDA!\nPRESSIONE ENTER PARA CONTINUAR");
                    dataFromClient.readUTF();
                    break;
            }
        }
    }

    public void addProductToSale(DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{
        
        dataToClient.writeUTF("DIGITE O NOME DO PRODUTO: ");
        String name = dataFromClient.readUTF();
        dataToClient.writeUTF("DIGITE O VALOR DO PRODUTO: ");
        double value = dataFromClient.readDouble();
        dataToClient.writeUTF("DIGITE A QUANTIDADE DISPONIVEL DESTE PRODUTO: ");
        int amount = (int)dataFromClient.readDouble();
        addProduct(name, value, amount);
        dataToClient.writeUTF("\nPRODUTO: " + name + " Por RS:" + value + " ADICIONADO\nPRESS ENTER PARA CONTINUAR"  );
        dataFromClient.readUTF();
    }
    
    public void listOfProductsAtSale(DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{

        String list = "";
        for (Product product : products) {
            if(product.getAmount() > 0){
                list += "\n#" + product.getName() + " | Amount avalible: " + product.getAmount() + "\n";
            } 
        }
        dataToClient.writeUTF(list);
        dataFromClient.readUTF();
    }
    
    public void showEarnedMoney(DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{

        double total_value = 0;
        for (Product product : products) {
            if(product.getSold() > 0){
                total_value += (product.getSold() * product.getUnitary_value());
            }
        }
        dataToClient.writeUTF("VALOR TOTAL GANHO DAS VENDA: " + total_value + "\n\nPRESS ENTER PARA CONTINUAR");
        dataFromClient.readUTF();
    }
    public void showProductsSold(DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{

        String list = "";
        for (Product product : products) {
            if(product.getSold() > 0){
                list += "\n# " + product.getName() + " | Amount sold: " + product.getSold() + "\n";
            }
        }
        dataToClient.writeUTF(list);
        dataFromClient.readUTF();   
    }
}