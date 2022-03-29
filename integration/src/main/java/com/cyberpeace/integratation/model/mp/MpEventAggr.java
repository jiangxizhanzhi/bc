package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpEventAggr implements Serializable {

	/**
	 * 文件百分比
	 */
	private Double file;

	/**
	 * 网络连接百分比
	 */
	private Double network;

	/**
	 * 端口百分比
	 */
	private Double port;

	/**
	 * 进程百分比
	 */
	private Double process;

	/**
	 * 用户百分比
	 */
	private Double user;

}
