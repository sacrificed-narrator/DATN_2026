package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import poly.edu.entity.Account;
import poly.edu.service.AccountService;
import poly.edu.service.AuthService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AuthService authService;

    @Autowired
    AccountService accountService;

    @GetMapping("/profile")
    public String profile(Model model) {

        if (!authService.isLogin()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("account", authService.getUser());
        return "account/profile";
    }

    // cập nhật thông tin
    @PostMapping("/update")
    public String update(
            @ModelAttribute("account") Account form,
            Model model) {

        Account user = authService.getUser();

        if (user == null) {
            return "redirect:/auth/login";
        }

        // chỉ cho sửa email + password
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());

        accountService.update(user);
        authService.login(user); // cập nhật lại session

        model.addAttribute("message", "Cập nhật thành công");
        model.addAttribute("account", user);

        return "account/profile";
    }
}
