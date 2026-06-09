package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poly.edu.service.*;

@Controller
public class AdminController {

    @Autowired
    AuthService authService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        // ✅ CHECK ADMIN
        if (!authService.isAdmin()) {
            return "redirect:/";
        }

        model.addAttribute("productCount", productService.findAll().size());
        model.addAttribute("orderCount", orderService.findAll().size());
        model.addAttribute("accountCount", accountService.findAll().size());

        double revenue = orderService.findAll().stream()
                .flatMap(o -> o.getOrderDetails().stream())
                .mapToDouble(d -> d.getPrice() * d.getQuantity())
                .sum();

        model.addAttribute("revenue", revenue);

        return "admin/dashboard";
    }
}
