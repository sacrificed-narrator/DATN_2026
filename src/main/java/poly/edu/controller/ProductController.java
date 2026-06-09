package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.service.ProductService;
import poly.edu.dao.CategoryDAO;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryDAO categoryDAO;

    @GetMapping("/category/{id}")
    public String listByCategory(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("products", productService.findByCategoryId(id));
        model.addAttribute("categories", categoryDAO.findAll());
        return "product/product-list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryDAO.findAll()); // ⭐ rất hay quên
        return "product/product-detail";
    }
}
