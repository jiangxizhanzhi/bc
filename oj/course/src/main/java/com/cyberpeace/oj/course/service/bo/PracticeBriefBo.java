package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习简报 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PracticeBriefBo implements Serializable {

	/**
	 * 准确性
	 */
	@ApiModelProperty("理论题正确率")
	private String accuracy;

	/**
	 * 水平
	 */
	@ApiModelProperty("评分等级")
	private Integer level=null;

	/**
	 * 对象计数
	 */
	@ApiModelProperty("客观题数量")
	private Integer objectCount;

	/**
	 * 主题计数
	 */
	@ApiModelProperty("主观题数量")
	private Integer subjectCount;

	/**
	 * 总计数
	 */
	@ApiModelProperty("题目总数量")
	private Integer totalCount;

}
