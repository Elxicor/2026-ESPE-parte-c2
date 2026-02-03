package es.upm.grise.profundizacion.order;

public class ItemImpl implements Item {
    
    private Product product;
    private double price;
    private int quantity;
    
    public ItemImpl(Product product, double price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }
    
    @Override
    public Product getProduct() {
        return this.product;
    }
    
    @Override
    public double getPrice() {
        return this.price;
    }
    
    @Override
    public int getQuantity() {
        return this.quantity;
    }
    
    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
