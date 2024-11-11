package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间安排 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class TimeScheduleBo implements Serializable {

	/**
	 * 教室
	 */
	@ApiModelProperty("课堂信息")
	private List<ClassroomBo> classrooms;

	/**
	 * 日期
	 */
	@ApiModelProperty("时间")
	private String date;

}
