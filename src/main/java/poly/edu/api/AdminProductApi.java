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
@RequestMapping("/api/admin/products")
@CrossOrigin("*")
public class AdminProductApi {
	@Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(@RequestBody Product product) {
        Product saved = productService.save(product);
        return ResponseEntity.ok(new ApiResponse<>(201, "Thêm mới thành công", saved));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy danh sách thành công", products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable("id") Integer id, @RequestBody Product product) {
        product.setId(id);
        Product updated = productService.save(product);
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật thành công", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Xóa thành công", null));
    }
}
