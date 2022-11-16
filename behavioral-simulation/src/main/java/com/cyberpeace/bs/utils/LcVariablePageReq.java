package com.cyberpeace.bs.utils;

import java.io.Serializable;

import lombok.Data;

@Data
public class LcVariablePageReq implements Serializable {

	private String name = "";

	private Integer page = 1;

	private String projectId = "1580451559792103425";

	private Integer size = 50;
}
