package poly.edu.service;

import java.util.Collection;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import poly.edu.model.CartItem;

public interface CartService {

    CartItem add(Integer productId);

    void remove(Integer productId);

    CartItem update(Integer productId, int quantity);

    void plus(Integer productId);

    void minus(Integer productId);

    void clear();

    Collection<CartItem> getItems();

    int getCount();

    double getAmount();
    
    List<CartItem> getCartItems(HttpSession session);
    double calculateTotal(List<CartItem> cart);
    void clearCart(HttpSession session);

}
