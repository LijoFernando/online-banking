package com.myWeb.model.pojo;

public class AuthInfo {
	private int AuthId;
	private String username;
	private String password;
	private String newPassword;
	private String email;
	private String usertype;

	public int getAuthId() {
		return AuthId;
	}

	public void setAuthId(int authId) {
		AuthId = authId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
