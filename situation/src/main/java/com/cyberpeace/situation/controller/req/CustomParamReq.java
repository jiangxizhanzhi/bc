package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * 自定义参数请求
 *
 * @Author xuxz
 * @Date 24/11/2022 上午11:56
 */
@Data
public class CustomParamReq implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 值
	 */
	private String value;

}
