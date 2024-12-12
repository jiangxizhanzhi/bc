package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Office 文件信息 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel("office文件信息")
public class OfficeFileInfoBo implements Serializable {

	/**
	 * 基础文件名称
	 */
	private String BaseFileName;

	/**
	 * 所有者id
	 */
	private String OwnerId;

	/**
	 * 大小
	 */
	private String Size;

	/**
	 * SHA256
	 */
	private String SHA256;

	/**
	 * 版本
	 */
	private String Version;

}
