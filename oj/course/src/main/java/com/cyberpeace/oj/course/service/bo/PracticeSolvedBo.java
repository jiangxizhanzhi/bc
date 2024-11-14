package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习解决Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeSolvedBo implements Serializable {

	/**
	 * 用户答案
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 是否回答正确
	 */
	@ApiModelProperty("是否回答正确")
	private Boolean solved;

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 得分
	 */
	@ApiModelProperty("得分")
	private Double score;

}
