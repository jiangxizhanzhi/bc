package com.cyberpeace.iov.model;

import java.util.List;

import lombok.Data;

@Data
public class CanBaseRsp {

	private List<String> code;

	private List<Object> data;

	private List<String> msg;
}
