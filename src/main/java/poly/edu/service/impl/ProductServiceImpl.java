package poly.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.edu.dao.ProductDAO;
import poly.edu.entity.Product;
import poly.edu.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> search(String keyword) {
        return productDAO.findAll()
                .stream()
                .filter(p -> p.getName()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()))
                .toList();
    }

    @Override
    public List<Product> findTopByCategory(Integer categoryId, int limit) {
        return productDAO.findByCategoryId(categoryId)
                .stream()
                .limit(limit)
                .toList();
    }
    
    @Override
    public Product save(Product product) {
        return productDAO.save(product);
    }

    @Override
    public void delete(Integer id) {
        productDAO.deleteById(id);
    }

}
