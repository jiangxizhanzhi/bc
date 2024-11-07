package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 知识类别新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class KnowledgeCategoryAddReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("知识点类别id")
	private String resourceId;

	/**
	 * 内容
	 */
	@NotBlank
	@ApiModelProperty("知识点类别名称")
	private String content;
}
