package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 关联计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RelCount implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	private Integer count;
}
