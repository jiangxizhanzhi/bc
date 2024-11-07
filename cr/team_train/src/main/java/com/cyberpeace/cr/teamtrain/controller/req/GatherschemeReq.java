package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * gather方案请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class GatherschemeReq implements Serializable {

	/**
	 * 方案
	 */
	private Object scheme;

	/**
	 * 点按 Flow
	 */
	private Boolean tapFlow;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

}