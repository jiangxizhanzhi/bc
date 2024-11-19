package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Office文件信息Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("office文件信息")
public class OfficeFileInfoBo implements Serializable {

	/**
	 * base文件名称
	 *
	 * @see String
	 */
	private String BaseFileName;

	/**
	 * 所有者id
	 *
	 * @see String
	 */
	private String OwnerId;

	/**
	 * 大小
	 *
	 * @see String
	 */
	private String Size;

	/**
	 * SHA256
	 *
	 * @see String
	 */
	private String SHA256;

	/**
	 * 版本
	 *
	 * @see String
	 */
	private String Version;

}
