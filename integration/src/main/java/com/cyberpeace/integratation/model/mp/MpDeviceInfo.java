package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;

/**
 * 中台装置信息
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class MpDeviceInfo implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 资源id
	 */
	private String resourceId;
}
