package com.yz.boster.rop.request;

import com.yz.boster.rop.BaseRopRequest;

@SuppressWarnings("serial")
public class AgentInfoRequest extends BaseRopRequest {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
