package poly.edu.service;

import java.util.List;

import poly.edu.entity.Account;

public interface AccountService {

    Account login(String username, String password);

    Account findByUsername(String username);

    Account update(Account account);
    	
    List<Account> findAll();

    Account save(Account account);
    
    void delete(String username);

}
