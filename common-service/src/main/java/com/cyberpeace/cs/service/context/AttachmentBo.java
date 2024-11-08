package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 文件路径
	 */
	@ApiModelProperty("文件路径")
	private String filePath;

	/**
	 * 是公共
	 */
	@ApiModelProperty("附件公开与隐藏状态 true=公开 false=隐藏")
	private Boolean isPublic;

}
