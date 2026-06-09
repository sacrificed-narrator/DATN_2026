package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.entity.Account;
import poly.edu.service.AccountService;
import poly.edu.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    AuthService authService;

    // GET: form login
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // POST: xử lý login
    @PostMapping("/login")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        Account user = accountService.login(username, password);

        if (user == null) {
            model.addAttribute("message", "Sai tài khoản hoặc mật khẩu");
            return "auth/login";
        }

        // ✅ lưu account vào session
        authService.login(user);

        return "redirect:/";
    }

    // logout
    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/auth/login";
    }
}
