package poly.edu.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.entity.Category;
import poly.edu.entity.Product;
import poly.edu.service.ProductService;
import poly.edu.service.CategoryService;

@Controller
public class HomeController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@GetMapping("/")
	public String home(@RequestParam(value = "cateId", required = false) Integer cateId,
			@RequestParam(value = "keyword", required = false) String keyword, Model model) {

		if (keyword != null) {
			keyword = keyword.trim();
		}
		if (cateId != null) {

			model.addAttribute("products", productService.findByCategoryId(cateId));

			model.addAttribute("view", "single");

		} else if (keyword != null && !keyword.isBlank()) {

			model.addAttribute("products", productService.search(keyword));

			model.addAttribute("view", "single");

		}


		else {

			List<Category> categories = categoryService.findAll();
			Map<Category, List<Product>> data = new LinkedHashMap<>();

			for (Category c : categories) {
				List<Product> list = productService.findTopByCategory(c.getId(), 6);
				data.put(c, list);
			}

			model.addAttribute("categoryProducts", data);
			model.addAttribute("view", "home");
		}

		return "home/index";
	}
}
