	package poly.edu.controller;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.*;
	
	import poly.edu.entity.Category;
	import poly.edu.entity.Product;
	import poly.edu.service.AuthService;
	import poly.edu.service.ProductService;
	import poly.edu.service.CategoryService;
	
	@Controller
	@RequestMapping("/admin/products")
	public class AdminProductController {
	
	    @Autowired
	    AuthService authService;
	
	    @Autowired
	    ProductService productService;
	
	    @Autowired
	    CategoryService categoryService;
	
	    // ===============================
	    // LIST
	    // ===============================
	    @GetMapping
	    public String products(Model model) {
	        if (!authService.isAdmin()) return "redirect:/";
	
	        model.addAttribute("products", productService.findAll());
	        return "admin/products";
	    }
	
	    // ===============================
	    // CREATE FORM
	    // ===============================
	    @GetMapping("/create")
	    public String createForm(Model model) {
	        Product product = new Product();
	        product.setCategory(new Category());

	        model.addAttribute("product", product);
	        model.addAttribute("categories", categoryService.findAll());
	        return "admin/product-form";
	    }

	
	    // ===============================
	    // EDIT FORM
	    // ===============================
	    @GetMapping("/edit/{id}")
	    public String editForm(@PathVariable Integer id, Model model) {
	        Product product = productService.findById(id);
	
	        if (product.getCategory() == null) {
	            product.setCategory(new Category());
	        }
	
	        model.addAttribute("product", product);
	        model.addAttribute("categories", categoryService.findAll());
	        return "admin/product-form";
	    }
	
	
	    // ===============================
	    // SAVE (CREATE + UPDATE)
	    // ===============================
	    @PostMapping("/save")
	    public String save(Product product) {

	        // Lấy category thật từ DB
	        Category category = categoryService.findById(
	                product.getCategory().getId()
	        );
	        product.setCategory(category);

	        productService.save(product);
	        return "redirect:/admin/products";
	    }

	
	    // ===============================
	    // DELETE
	    // ===============================
	    @GetMapping("/delete/{id}")
	    public String delete(@PathVariable Integer id) {
	        productService.delete(id);
	        return "redirect:/admin/products";
	    }
	}
