package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * gather方案请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class GatherschemeReq  {

	/**
	 * 方案
	 */
	private Object scheme;

	/**
	 * 水龙头流量
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