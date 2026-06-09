package poly.edu.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.edu.entity.Account;
import poly.edu.service.AccountService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthApi {
	@Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Account>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Account acc = accountService.findByUsername(username);
        if (acc != null && acc.getPassword().equals(password)) {
            acc.setPassword(null);
            return ResponseEntity.ok(new ApiResponse<>(200, "Đăng nhập thành công", acc));
        }
        return ResponseEntity.status(401).body(new ApiResponse<>(401, "Sai tài khoản hoặc mật khẩu", null));
    }
}
