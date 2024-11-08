package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 牌号分布 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class GradeDistributionBo implements Serializable {

	/**
	 * 非常好
	 */
	@ApiModelProperty("良好")
	private Integer excellent = 0;

	/**
	 * 满分
	 */
	@ApiModelProperty("满分")
	private Integer fullScore = 0;

	/**
	 * 好
	 */
	@ApiModelProperty("优秀")
	private Integer good = 0;

	/**
	 * 未通过
	 */
	@ApiModelProperty("未通过")
	private Integer notPass = 0;

	/**
	 * 通过
	 */
	@ApiModelProperty("及格")
	private Integer pass = 0;

}
