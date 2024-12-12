package com.cyberpeace.cr.crbase.service.context.mark;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 证据 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvidenceBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "图片上传后返回的resource_id")
	private String resourceId;

	/**
	 * 文件名
	 */
	@ApiModelProperty(value = "文件名")
	private String filename;

	/**
	 * 路径
	 */
	@ApiModelProperty(value = "文件路径")
	private String path;

}
