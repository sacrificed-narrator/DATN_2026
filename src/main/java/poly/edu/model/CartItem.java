package poly.edu.model;

import lombok.Data;

@Data
public class CartItem {

    private Integer id;
    private String name;
    private String image;
    private Double price;
    private int quantity;

    public CartItem(Integer id, String name, String image, Double price, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
