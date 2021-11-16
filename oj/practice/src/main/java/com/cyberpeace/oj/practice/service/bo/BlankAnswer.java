package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlankAnswer implements Serializable {

	/** 权重*/
	private Integer weight;

	/** 答案*/
	private String value;

}
