package com.yz.boster.rop;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rop.AbstractRopRequest;

@SuppressWarnings("serial")
public class BaseRopRequest extends AbstractRopRequest implements Serializable {

	/**
	 * 业务请求来源 1:IOS 20:android 30:H5 40:PC
	 */
	@NotNull
	private Integer from;

	// For methods in session scope (NeedInSessionType.YES),it must be the ROP session id.
	private String sessionId;

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
