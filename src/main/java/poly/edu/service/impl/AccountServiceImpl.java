package poly.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.edu.dao.AccountDAO;
import poly.edu.entity.Account;
import poly.edu.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDAO accountDAO;

    @Override
    public Account login(String username, String password) {
        return accountDAO.findByUsernameAndPassword(username, password);
    }

    @Override
    public Account findByUsername(String username) {
        return accountDAO.findById(username).orElse(null);
    }

    @Override
    public Account update(Account account) {
        return accountDAO.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountDAO.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountDAO.save(account);
    }

    @Override
    public void delete(String username) {
        accountDAO.deleteById(username);
    }
}
