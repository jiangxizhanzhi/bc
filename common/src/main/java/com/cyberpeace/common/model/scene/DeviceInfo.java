package com.cyberpeace.common.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 设备信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class DeviceInfo implements Serializable {

	private String resourceId;

	private String name;

}
