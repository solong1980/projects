package com.yz.boster.rop.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.yz.boster.model.User;
import com.yz.boster.rop.BaseRopRequest;

@SuppressWarnings("serial")
public class AgentRegistRequest extends BaseRopRequest {

	@NotNull
	private String loginName;
	@NotNull
	private String userName;
	private String password;
	private Integer sex;
	private Integer age;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public User getUser() {
		User user = new User();
		user.setLoginName(getLoginName());
		user.setName(getUserName());
		user.setPassword(getPassword());
		user.setAge(getAge());
		user.setSex(getSex());
		
		user.setCreateTime(new Date());
		return user;
	}
}
