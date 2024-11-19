package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;

/**
 * 中台装置信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpDeviceInfo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;
}
