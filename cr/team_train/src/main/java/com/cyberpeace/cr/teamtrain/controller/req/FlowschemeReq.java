package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * 流量程图请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowschemeReq implements Serializable {

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

	/**
	 * 方案
	 *
	 * @see Object
	 */
	private Object scheme;

}