package com.cyberpeace.bs.utils;

import java.io.Serializable;

import lombok.Data;

@Data
public class LcVariablePageReq implements Serializable {

	private String name = "";

	private Integer page = 1;

	private String projectId;

	private Integer size = 50;

	public LcVariablePageReq(String projectId) {
		this.projectId = projectId;
	}
}
