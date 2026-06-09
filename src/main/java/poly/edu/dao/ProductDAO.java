package poly.edu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {

    // ✅ ĐÚNG – JPA sẽ tự hiểu category.id
    List<Product> findByCategoryId(Integer categoryId);
}
