package com.cyberpeace.common.model.scene;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 设备信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DeviceInfo implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

}
