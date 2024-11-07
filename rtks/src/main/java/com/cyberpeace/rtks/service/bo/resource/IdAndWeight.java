package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;

import lombok.Data;

/**
 * id和重量
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class IdAndWeight implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 重量
	 */
	private Integer weight;
}
