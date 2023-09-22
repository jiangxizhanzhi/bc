package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;

import lombok.Data;

/**
 * 远程设备请求体
 *
 * @author SN
 * @date 2023/06/19
 */
@Data
public class LcDeviceReq implements Serializable {

	/**
	 * 设备类型
	 */
	private Integer deviceType = 0;

	/**
	 * 分页
	 */
	private Integer page = 1;

	/**
	 * 大小
	 */
	private Integer size = 99999;

	/**
	 * 项目id
	 */
	private Integer projectId = 0;

	/**
	 * uuid
	 */
	private String uuid;
}
