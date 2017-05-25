package com.yz.boster.rop.request;

import com.yz.boster.rop.BaseRopRequest;

@SuppressWarnings("serial")
public class AgentInfoRequest extends BaseRopRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
