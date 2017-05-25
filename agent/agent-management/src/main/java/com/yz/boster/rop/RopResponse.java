package com.yz.boster.rop;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class RopResponse implements Serializable {
	private int code;
	private String message;

	// After login ,you must take this back to client
	private String sessionId;

	private Serializable modelVo;

	private List<Serializable> array;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Serializable getModelVo() {
		return modelVo;
	}

	public void setModelVo(Serializable modelVo) {
		this.modelVo = modelVo;
	}

	public List<Serializable> getArray() {
		return array;
	}

	public void setArray(List<Serializable> array) {
		this.array = array;
	}

}
