package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class GradeDistributionBo implements Serializable {

	@ApiModelProperty("良好")
	private Integer excellent = 0;

	@ApiModelProperty("满分")
	private Integer fullScore = 0;

	@ApiModelProperty("优秀")
	private Integer good = 0;

	@ApiModelProperty("未通过")
	private Integer notPass = 0;

	@ApiModelProperty("及格")
	private Integer pass = 0;

}
