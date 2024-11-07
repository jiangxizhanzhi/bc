package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

/**
 * 多角色上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MultiRoleContext implements Serializable {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 计数
	 */
	private Integer count;
}
