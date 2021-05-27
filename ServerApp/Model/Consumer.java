package ServerApp.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class Consumer extends Client{

    private double CashAvaliable;
    private ArrayList<PedidoClient> itemsComprados;
    
    public Consumer(String name, String email, String password){
        super(name, email, password);
        this.itemsComprados = new ArrayList<PedidoClient>();
        this.CashAvaliable = 0;
    }

    public double getCashAvaliable() {
        return CashAvaliable;
    }
    public void setCashAvaliable(double cashAvaliable) {
        CashAvaliable = cashAvaliable;
    }
    public void SetPlusCash(double value){
        if(value > 0){
            this.CashAvaliable += value;
        }
    }

    @Override
    public void inicar_sessao(DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception{
        String option = "";
        dataToClient.writeUTF("\nBEM VINDO " + this.getName() + " PRESSIONE ENTER PARA CONTINUAR");
        dataFromClient.readUTF();
        while(!option.equalsIgnoreCase("voltar")){
            
            dataToClient.writeUTF("1 - COMPRAR PRODUTO\n2 - VER CASH DISPONIVEL \n3 - ADICIONAR CASH\n4 - VER COMPRAS REALIZADAS\n5 - PARA VOLTAR");
            option = dataFromClient.readUTF();

            switch (option) {
                case "1":
                    comprar_produto(dataFromClient, dataToClient, managerClients);
                    break;
                case "2":
                    dataToClient.writeUTF("CASH DISPONIVEL: " + getCashAvaliable() + "\nPRESS ENTER PARA CONTINUAR");
                    dataFromClient.readUTF();
                    break;
                case "3":
                    addCash(dataFromClient, dataToClient);
                    break;
                case "4":
                    listOfBuys(dataFromClient, dataToClient);
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

    public void addCash(DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{

        dataToClient.writeUTF("DIGITE O SEU CARTAO DE CREDITO: ");
        dataFromClient.readUTF();
        dataToClient.writeUTF("QUANTO DESEJA ADICIONAR AO CASH: ");
        SetPlusCash(dataFromClient.readDouble());
        dataToClient.writeUTF("VALOR DISPONIVEL DE CASH: " + getCashAvaliable() + "\nPRESS ENTER PARA CONTINUAR");
        dataFromClient.readUTF();
    } 

    public void listOfBuys(DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{
        
        String list = "";
        for (PedidoClient pedidoClient : itemsComprados) {
            list += "# " + pedidoClient.getName() + "| RS: " + pedidoClient.getValue() + "\n";
        }
        dataToClient.writeUTF(list);
        dataFromClient.readUTF();
    }

    public void comprar_produto(DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception{

        dataToClient.writeUTF("\n------------------\n1 - BUSCA PRODUTO POR NOME\n2 - BUSCA POR VENDEDOR\n");
        String option = dataFromClient.readUTF();
        if(option.equalsIgnoreCase("1")){
            dataToClient.writeUTF("DIGITE O NOME DO PRODUTO: ");
            ArrayList<Product> products = managerClients.buscar(dataFromClient.readUTF());
            if(!products.isEmpty()){
                processar_busca(products, dataFromClient, dataToClient, managerClients);
            }else{
                dataToClient.writeUTF("PRODUTO NAO ENCONTRADO\n\nPRESS ENTER PARA CONTINUAR\n");
                dataFromClient.readUTF();
            }

        }else{

            String sallers = listOfSallers(managerClients);
            dataToClient.writeUTF(sallers);
            dataFromClient.readUTF();
            dataToClient.writeUTF("\n ESCOLHA O VENDEDOR:\n");
            int index = (int)dataFromClient.readDouble();


            Client client = managerClients.getListOfClients().get(index);
            if(client instanceof Saller){
                Saller vendedor = (Saller) client;
                if(!vendedor.getProducts().isEmpty()){
                    processar_busca(vendedor.getProducts(), dataFromClient, dataToClient, managerClients);
                }
                
            }
        }
    }
    
    public void processar_busca(ArrayList<Product> products, DataInputStream dataFromClient, DataOutputStream dataToClient, ManagerClients managerClients) throws Exception{

        String list = "";
        for (Product product : products) {
            list += products.indexOf(product) + " | " + product.getName() + " RS: " + product.getUnitary_value() + " - AMOUNT: " + product.getAmount() + "\n";
        }
        dataToClient.writeUTF(list + "PRESS ENTER PARA CONTINUAR");
        dataFromClient.readUTF();
        dataToClient.writeUTF("SELECIONE O PRODUTO: ");
        int index = (int)dataFromClient.readDouble();

        Product product = products.get(index);
        processar_compra(product, dataFromClient, dataToClient);
    }

    public void processar_compra(Product product, DataInputStream dataFromClient, DataOutputStream dataToClient) throws Exception{

        dataToClient.writeUTF("Qual a quantidade do produto");
        int amount = (int) dataFromClient.readDouble();
        if((getCashAvaliable() >= product.getUnitary_value() * amount) && product.getAmount() >= amount){
            product.setAmount((product.getAmount() - amount));
            product.setSold(amount);
            setCashAvaliable(getCashAvaliable() - (product.getUnitary_value()*amount));           
            itemsComprados.add(new PedidoClient(product.getName(), product.getUnitary_value(), amount));

            dataToClient.writeUTF("COMPRADO COM SUCESSO - INFO:\n" + "PRODUTO: " + product.getName() + "\nPRECO: " + product.getUnitary_value() + "\nQUANT: " + amount);
            dataFromClient.readUTF();
        }else{

            dataToClient.writeUTF("NAO FOI POSSIVEL REALIZAR A COMPRA\nTECLE ENTER PARA CONTINUAR\nno cash\n");
            dataFromClient.readUTF();
        }       
    }

    public String listOfSallers(ManagerClients clients){

        String list = "";
        ArrayList<Client> clientes = clients.getListOfClients();
        for (Client client : clientes) {
            if(client instanceof Saller){
                list += "# " + clientes.indexOf(client) + " | " + client.getName() + "\n";
            }
        }

        return list;

    }
}