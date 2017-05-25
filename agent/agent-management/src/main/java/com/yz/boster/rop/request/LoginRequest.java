package com.yz.boster.rop.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.rop.annotation.IgnoreSign;
import com.yz.boster.rop.BaseRopRequest;

@SuppressWarnings("serial")
public class LoginRequest extends BaseRopRequest {
	@NotNull
	@Pattern(regexp = "\\w{4,30}")
	private String userName;

	@IgnoreSign
	@Pattern(regexp = "\\w{1,30}")
	private String password;

	private int rememberMe;

	public int getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(int rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
