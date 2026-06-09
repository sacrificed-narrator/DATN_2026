package poly.edu.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import poly.edu.entity.Account;
import poly.edu.service.AuthService;

@SessionScope
@Service
public class AuthServiceImpl implements AuthService {

	private Account user;

	@Override
	public void login(Account user) {
		this.user = user;
	}

	@Override
	public void logout() {
		this.user = null;
	}

	@Override
	public boolean isLogin() {
		return user != null;
	}

	@Override
	public Account getUser() {
		return user;
	}

	@Override
	public boolean isAdmin() {
		return isLogin() && "ADMIN".equalsIgnoreCase(user.getRole());
	}
}
