package poly.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import poly.edu.entity.Category;
import poly.edu.service.AuthService;
import poly.edu.service.CategoryService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    AuthService authService;

    @Autowired
    CategoryService categoryService;

    // Auth dùng cho menu login / logout
    @ModelAttribute("auth")
    public AuthService auth() {
        return authService;
    }

    // Categories dùng cho menu
    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }
}
