package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * gather方案请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class GatherschemeReq implements Serializable {

	/**
	 * 方案
	 *
	 * @see Object
	 */
	private Object scheme;

	/**
	 * 点按 Flow
	 *
	 * @see Boolean
	 */
	private Boolean tapFlow;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

}