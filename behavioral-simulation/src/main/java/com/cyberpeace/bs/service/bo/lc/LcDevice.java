package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;

import lombok.Data;

/**
 * 远程设备
 *
 * @author SN
 * @date 2023/06/19
 */
@Data
public class LcDevice implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 序列号
	 */
	private String serialNo;

	/**
	 * 设备标识符
	 */
	private String deviceIdentifier;

	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * ip地址
	 */
	private String inAddress;

	/**
	 * 出地址
	 */
	private String outAddress;
}
