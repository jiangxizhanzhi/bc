package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * 流程图请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class FlowschemeReq implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 方案
	 */
	private Object scheme;

}