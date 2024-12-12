package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 牌号分布 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class GradeDistributionBo implements Serializable {

	/**
	 * 非常好
	 *
	 * @see Integer
	 */
	@ApiModelProperty("良好")
	private Integer excellent = 0;

	/**
	 * 满分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("满分")
	private Integer fullScore = 0;

	/**
	 * 好
	 *
	 * @see Integer
	 */
	@ApiModelProperty("优秀")
	private Integer good = 0;

	/**
	 * 未通过
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未通过")
	private Integer notPass = 0;

	/**
	 * 通过
	 *
	 * @see Integer
	 */
	@ApiModelProperty("及格")
	private Integer pass = 0;

}
