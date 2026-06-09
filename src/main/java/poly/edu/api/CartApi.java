package poly.edu.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.edu.model.CartItem;
import poly.edu.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartApi {
	@Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCartInfo() {
        Collection<CartItem> items = cartService.getItems();
        
        Map<String, Object> cartData = new HashMap<>();
        cartData.put("items", items);
        cartData.put("totalCount", cartService.getCount());
        cartData.put("totalAmount", cartService.getAmount());

        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy giỏ hàng thành công", cartData));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<ApiResponse<CartItem>> addToCart(@PathVariable("productId") Integer productId) {
        CartItem item = cartService.add(productId); 
        return ResponseEntity.ok(new ApiResponse<>(200, "Đã thêm vào giỏ hàng", item));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse<CartItem>> updateCart(
            @PathVariable("productId") Integer productId, 
            @RequestParam("qty") int qty) {
        
        if(qty <= 0) {
            cartService.remove(productId);
            return ResponseEntity.ok(new ApiResponse<>(200, "Đã xóa sản phẩm vì số lượng = 0", null));
        }
        
        CartItem item = cartService.update(productId, qty);
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật số lượng thành công", item));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(@PathVariable("productId") Integer productId) {
        cartService.remove(productId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Đã xóa khỏi giỏ hàng", null));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clear();
        return ResponseEntity.ok(new ApiResponse<>(200, "Đã làm sạch giỏ hàng", null));
    }
}
