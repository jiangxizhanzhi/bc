package com.cyberpeace.common.model.scene;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class DeviceInfo implements Serializable {

	private String resourceId;

	private String name;

}
