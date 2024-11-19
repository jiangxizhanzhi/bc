package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识点信息Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("知识点信息基本")
public class KnowledgeInfoBo implements Serializable {

	/**
	 * 知识点编号
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点编号")
    private String resourceId;

	/**
	 * 知识点分类编号
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点分类编号")
    private String categoryId;

	/**
	 * 知识点内容
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点内容")
    private String content;

}
