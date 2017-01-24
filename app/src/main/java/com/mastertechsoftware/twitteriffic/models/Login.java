package com.mastertechsoftware.twitteriffic.models;

/**
 * Login Information
 */
public class Login {
	private String username;
	private String password;
	private boolean loginSuccessful;

	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
