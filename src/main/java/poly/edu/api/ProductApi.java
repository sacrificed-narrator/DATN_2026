package poly.edu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.edu.entity.Product;
import poly.edu.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductApi {
	@Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAll() {
        List<Product> list = productService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Thành công", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable("id") Integer id) {
        Product p = productService.findById(id);
        if (p == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Không tìm thấy game", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Thành công", p));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.save(product);
            return ResponseEntity.ok(new ApiResponse<>(201, "Thêm mới thành công", savedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Lỗi thêm mới: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable("id") Integer id, 
            @RequestBody Product product) {
        try {
            product.setId(id); 
            Product updatedProduct = productService.save(product);
            return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật thành công", updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Lỗi cập nhật: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") Integer id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Xóa sản phẩm thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Không thể xóa sản phẩm này (có thể do dính khóa ngoại đơn hàng)", null));
        }
    }
}
