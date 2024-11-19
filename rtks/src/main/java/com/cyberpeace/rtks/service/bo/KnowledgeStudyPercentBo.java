package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 知识点完成度统计模型
 *
 * @author yinqingrong@see Serializable
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("知识点进度计算")
public class KnowledgeStudyPercentBo implements Serializable {

	/**
	 * 知识点编号
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点编号")
	private String knowledgeId;

	/**
	 * 知识点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点名称")
	private String knowledgeName;

	/**
	 * 知识点完成百分比
	 *
	 * @see Integer
	 */
	@ApiModelProperty("知识点完成百分比")
	private Integer percent;
}
