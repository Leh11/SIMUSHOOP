package ServerApp.Model;


public class Product {

    private String name;
    private int sold;
    private double unitary_value;
    private int amount;
    //atributo link para fotos do produto

    public Product(String name, double unitary_value, int amount){
        this.name = name;
        this.unitary_value = unitary_value;
        this.amount = amount;
        this.sold = 0;
    }
    public String getName() {
        return name;
    }
    public int getSold() {
        return sold;
    }
    public double getUnitary_value() {
        return unitary_value;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setSold(int sold) {
        this.sold = sold;
    }
}
