package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 知识点类别新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class KnowledgeCategoryAddReq implements Serializable {

	/**
	 * 知识点类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点类别id")
	private String resourceId;

	/**
	 * 知识点类别名称
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty("知识点类别名称")
	private String content;
}
