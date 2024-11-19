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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class TimeScheduleBo implements Serializable {

	/**
	 * 课堂信息
	 *
	 * @see List<ClassroomBo>
	 */
	@ApiModelProperty("课堂信息")
	private List<ClassroomBo> classrooms;

	/**
	 * 时间
	 *
	 * @see String
	 */
	@ApiModelProperty("时间")
	private String date;

}
