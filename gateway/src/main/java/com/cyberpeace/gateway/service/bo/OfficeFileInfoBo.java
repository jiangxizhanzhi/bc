package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Office文件信息Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("office文件信息")
public class OfficeFileInfoBo implements Serializable {

	/**
	 * base文件名称
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
