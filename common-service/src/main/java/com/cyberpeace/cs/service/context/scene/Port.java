package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 港口
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class Port implements Serializable {

	/**
	 * ip地址
	 *
	 * @see String
	 */
	private String ip;
}
