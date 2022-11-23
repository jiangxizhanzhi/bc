package com.cyberpeace.bs.utils.model;

import lombok.Data;

@Data
public class LcResponse<T> {

	public T data;

	public String code;

	public String msg;
}
