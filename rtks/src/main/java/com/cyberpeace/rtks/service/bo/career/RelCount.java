package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 相对计数
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RelCount implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 计数
	 */
	private Integer count;
}
