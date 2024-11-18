package com.cyberpeace.common.model.scene;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DeviceInfo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

}
