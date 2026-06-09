package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.edu.entity.Account;
import poly.edu.service.AccountService;
import poly.edu.service.AuthService;

@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController {

    @Autowired
    AuthService authService;

    @Autowired
    AccountService accountService;

    @GetMapping
    public String accounts(Model model) {
        if (!authService.isAdmin()) return "redirect:/";

        model.addAttribute("accounts", accountService.findAll());
        return "admin/accounts";
    }

    // ➕ CREATE
    @GetMapping("/create")
    public String createForm(Model model) {
        if (!authService.isAdmin()) return "redirect:/";

        model.addAttribute("account", new Account());
        return "admin/account-form";
    }

    // ✏️ EDIT
    @GetMapping("/edit/{username}")
    public String editForm(@PathVariable String username, Model model) {
        if (!authService.isAdmin()) return "redirect:/";

        model.addAttribute("account", accountService.findByUsername(username));
        return "admin/account-form";
    }

    // 💾 SAVE (cả thêm & sửa)
    @PostMapping("/save")
    public String save(Account account) {
        accountService.save(account);
        return "redirect:/admin/accounts";
    }

    // ❌ DELETE
    @GetMapping("/delete/{username}")
    public String delete(@PathVariable String username) {
        accountService.delete(username);
        return "redirect:/admin/accounts";
    }
}
