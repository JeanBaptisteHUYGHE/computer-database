package com.excilys.cdb.controller.session;

import org.springframework.stereotype.Component;

@Component
public class UserSession {

	private Integer pageSize;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String toString() {
		return String.format("<UserSession: pageSize=%s>", pageSize);
	}
}
