package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.entity.Category;
import poly.edu.service.AuthService;
import poly.edu.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    AuthService authService;

    @Autowired
    CategoryService categoryService;

    // =========================
    // LIST
    // =========================
    @GetMapping
    public String categories(Model model) {
        if (!authService.isAdmin()) return "redirect:/";

        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    // =========================
    // CREATE
    // =========================
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    // =========================
    // EDIT
    // =========================
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category-form";
    }

    // =========================
    // SAVE (CREATE + UPDATE)
    // =========================
    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // =========================
    // DELETE
    // =========================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
}
