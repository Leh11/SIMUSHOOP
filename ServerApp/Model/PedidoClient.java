package ServerApp.Model;

public class PedidoClient {
    
    private String name;
    private double value;
    private int amount;

    public PedidoClient(String name, double value, int amount){
        this.name = name;
        this.value = value;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public double getValue() {
        return value;
    }
    public int getAmount() {
        return amount;
    }

    //link foto
}
