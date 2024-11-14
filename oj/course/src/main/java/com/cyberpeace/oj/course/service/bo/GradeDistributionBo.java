package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 年级分配Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class GradeDistributionBo implements Serializable {

	/**
	 * 良好
	 */
	@ApiModelProperty("良好")
	private Integer excellent = 0;

	/**
	 * 满分
	 */
	@ApiModelProperty("满分")
	private Integer fullScore = 0;

	/**
	 * 优秀
	 */
	@ApiModelProperty("优秀")
	private Integer good = 0;

	/**
	 * 未通过
	 */
	@ApiModelProperty("未通过")
	private Integer notPass = 0;

	/**
	 * 及格
	 */
	@ApiModelProperty("及格")
	private Integer pass = 0;

}
