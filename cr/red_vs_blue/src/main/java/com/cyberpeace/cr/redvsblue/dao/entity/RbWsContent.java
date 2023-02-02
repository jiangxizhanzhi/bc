package com.cyberpeace.cr.redvsblue.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RbWsContent<T> implements Serializable {

	private T data;

	private Integer code;

	private Integer group;
}
