package com.yz.boster.rop.request;

import com.yz.boster.rop.BaseRopRequest;

public class PageQueryRequest extends BaseRopRequest {

	protected Integer offset = 0;

	protected Integer limit = 10;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
