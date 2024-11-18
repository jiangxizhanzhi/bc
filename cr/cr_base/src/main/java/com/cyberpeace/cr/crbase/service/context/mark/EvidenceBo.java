package com.cyberpeace.cr.crbase.service.context.mark;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 证据 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvidenceBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "图片上传后返回的resource_id")
	private String resourceId;

	/**
	 * 文件名
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "文件名")
	private String filename;

	/**
	 * 路径
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "文件路径")
	private String path;

}
