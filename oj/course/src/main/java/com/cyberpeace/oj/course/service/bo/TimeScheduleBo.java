package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间附表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class TimeScheduleBo implements Serializable {

	/**
	 * 课堂信息
	 */
	@ApiModelProperty("课堂信息")
	private List<ClassroomBo> classrooms;

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private String date;

}
