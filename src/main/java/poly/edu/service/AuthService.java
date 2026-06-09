package poly.edu.service;

import poly.edu.entity.Account;

public interface AuthService {

    void login(Account user);

    void logout();

    boolean isLogin();

    Account getUser();

    boolean isAdmin();
}
