package es.upm.grise.profundizacion.order;

import java.util.ArrayList;
import java.util.Collection;

public class Order {

    private Collection<Item> items;

    /*
     * Constructor
     */
    public Order() {
        this.items = new ArrayList<Item>();
    }

    /*
     * Method to code / test
     */
    public void addItem(Item item) throws IncorrectItemException {
        if (item.getPrice() < 0) {
            throw new IncorrectItemException();
        }

        if (item.getQuantity() <= 0) {
            throw new IncorrectItemException();
        }

        for (Item existingItem : items) {
            if (existingItem.getProduct().equals(item.getProduct())) {
                
                if (existingItem.getPrice() == item.getPrice()) {
                    int newQuantity = existingItem.getQuantity() + item.getQuantity();
                    existingItem.setQuantity(newQuantity);
                    return; 
                }
                
            }
        }

        this.items.add(item);
    }
    
    /*
     * Setters/getters
     */
    public Collection<Item> getItems() {
        return this.items;
    }

}

