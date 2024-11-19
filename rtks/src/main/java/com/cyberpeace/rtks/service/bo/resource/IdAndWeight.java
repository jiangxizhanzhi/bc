package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;

import lombok.Data;

/**
 * id和权重
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class IdAndWeight implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 权重
	 *
	 * @see Integer
	 */
	private Integer weight;
}
