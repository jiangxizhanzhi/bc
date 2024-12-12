package com.cyberpeace.integratation.model.mp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 中台装置信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
