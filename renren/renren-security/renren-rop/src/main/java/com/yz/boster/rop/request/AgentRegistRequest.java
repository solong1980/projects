package com.yz.boster.rop.request;

import io.renren.entity.UserEntity;

import java.util.Date;

import javax.validation.constraints.NotNull;

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

	public UserEntity getUser() {
		UserEntity user = new UserEntity();
		user.setUsername(getLoginName());
		user.setPassword(getPassword());
		user.setCreateTime(new Date());
		return user;
	}
}
