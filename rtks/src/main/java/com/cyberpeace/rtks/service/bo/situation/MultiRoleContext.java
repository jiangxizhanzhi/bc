package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

/**
 * 多岗位上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MultiRoleContext implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	private Integer count;
}
