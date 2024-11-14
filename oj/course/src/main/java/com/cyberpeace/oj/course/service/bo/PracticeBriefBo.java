package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习短Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeBriefBo implements Serializable {

	/**
	 * 理论题正确率
	 */
	@ApiModelProperty("理论题正确率")
	private String accuracy;

	/**
	 * 评分等级
	 */
	@ApiModelProperty("评分等级")
	private Integer level=null;

	/**
	 * 客观题数量
	 */
	@ApiModelProperty("客观题数量")
	private Integer objectCount;

	/**
	 * 主观题数量
	 */
	@ApiModelProperty("主观题数量")
	private Integer subjectCount;

	/**
	 * 题目总数量
	 */
	@ApiModelProperty("题目总数量")
	private Integer totalCount;

}
