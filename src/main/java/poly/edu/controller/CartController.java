package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.service.AuthService;
import poly.edu.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    AuthService authService;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("cart", cartService.getItems());
        model.addAttribute("total", cartService.getAmount());
        return "cart/cart";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Integer id) {
        cartService.add(id);
        return "redirect:/cart/view";
    }

    // ✅ TĂNG
    @GetMapping("/plus/{id}")
    public String plus(@PathVariable("id") Integer id) {
        cartService.plus(id);
        return "redirect:/cart/view";
    }

    // ✅ GIẢM
    @GetMapping("/minus/{id}")
    public String minus(@PathVariable("id") Integer id) {
        cartService.minus(id);
        return "redirect:/cart/view";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id) {
        cartService.remove(id);
        return "redirect:/cart/view";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam("qty") Integer qty) {
        cartService.update(id, qty);
        return "redirect:/cart/view";
    }

    @GetMapping("/clear")
    public String clear() {
        cartService.clear();
        return "redirect:/cart/view";
    }
}
