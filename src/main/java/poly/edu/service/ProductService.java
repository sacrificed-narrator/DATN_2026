package poly.edu.service;

import java.util.List;
import poly.edu.entity.Product;

public interface ProductService {

    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> search(String keyword);

    List<Product> findTopByCategory(Integer categoryId, int limit);
    
    Product save(Product product);
    
    void delete(Integer id);

}

