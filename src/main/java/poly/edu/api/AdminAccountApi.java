package poly.edu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.edu.entity.Account;
import poly.edu.service.AccountService;

@RestController
@RequestMapping("/api/admin/accounts")
@CrossOrigin("*")
public class AdminAccountApi {
	@Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        for (Account acc : accounts) {
            acc.setPassword(null);
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy danh sách thành công", accounts));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<Account>> getAccountByUsername(@PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        if (account == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Không tìm thấy tài khoản", null));
        }
        account.setPassword(null);
        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy thông tin thành công", account));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody Account account) {
        try {
            if (accountService.findByUsername(account.getUsername()) != null) {
                return ResponseEntity.status(400).body(new ApiResponse<>(400, "Tên đăng nhập đã tồn tại", null));
            }
            Account savedAccount = accountService.save(account);
            savedAccount.setPassword(null);
            return ResponseEntity.ok(new ApiResponse<>(201, "Tạo tài khoản thành công", savedAccount));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Lỗi tạo tài khoản: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<ApiResponse<Account>> updateAccount(
            @PathVariable("username") String username, 
            @RequestBody Account account) {
        try {
            Account existingAccount = accountService.findByUsername(username);
            if (existingAccount == null) {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Không tìm thấy tài khoản để cập nhật", null));
            }
            
            account.setUsername(username); 
            
            if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
                account.setPassword(existingAccount.getPassword());
            }

            Account updatedAccount = accountService.update(account);
            updatedAccount.setPassword(null);
            return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật thành công", updatedAccount));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Lỗi cập nhật: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable("username") String username) {
        try {
            accountService.delete(username);
            return ResponseEntity.ok(new ApiResponse<>(200, "Xóa tài khoản thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Không thể xóa tài khoản này (có thể do họ đã có đơn hàng trong hệ thống)", null));
        }
    }
}
