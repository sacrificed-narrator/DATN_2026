package poly.edu.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.servlet.http.HttpSession;
import poly.edu.dao.ProductDAO;
import poly.edu.entity.Product;
import poly.edu.model.CartItem;
import poly.edu.service.CartService;

@SessionScope
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductDAO productDAO;

    Map<Integer, CartItem> cart = new HashMap<>();

    @Override
    public CartItem add(Integer productId) {
        CartItem item = cart.get(productId);

        if (item == null) {
            Product p = productDAO.findById(productId).orElse(null);
            if (p != null) {
                cart.put(productId, new CartItem(
                        p.getId(),
                        p.getName(),
                        p.getImage(),
                        p.getPrice(),
                        1
                ));
            }
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
		return item;
    }

    @Override
    public void plus(Integer productId) {
        CartItem item = cart.get(productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    @Override
    public void minus(Integer productId) {
        CartItem item = cart.get(productId);
        if (item != null) {
            int qty = item.getQuantity() - 1;
            if (qty <= 0) {
                cart.remove(productId);
            } else {
                item.setQuantity(qty);
            }
        }
    }

    @Override
    public void remove(Integer productId) {
        cart.remove(productId);
    }

    @Override
    public CartItem update(Integer productId, int quantity) {
        CartItem item = cart.get(productId);
        if (item != null) {
            item.setQuantity(quantity);
        }
		return item;
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public Collection<CartItem> getItems() {
        return cart.values();
    }

    @Override
    public int getCount() {
        return cart.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Override
    public double getAmount() {
        return cart.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
    
    @Override
    public List<CartItem> getCartItems(HttpSession session) {
        // Vì class là @SessionScope, ta có thể trả về trực tiếp từ map hiện tại
        return new java.util.ArrayList<>(cart.values());
    }

    @Override
    public double calculateTotal(List<CartItem> cartList) {
        return cartList.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    @Override
    public void clearCart(HttpSession session) {
        this.clear();
    }
}
