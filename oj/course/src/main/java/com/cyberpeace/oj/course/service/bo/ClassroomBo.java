package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class ClassroomBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("结束时间")
	private Date endTime;

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("课程名称")
	private String courseName;

	@ApiModelProperty("课程类型")
	private Integer courseType;

	@ApiModelProperty("课时编号")
	private String lessonId;

	@ApiModelProperty("课时名称")
	private String lessonName;

	@ApiModelProperty("课时类型")
	private Integer lessonType;

	@ApiModelProperty("组织编号")
	private String organizationId;

	@ApiModelProperty("组织名称")
	private String organizationName;

	@ApiModelProperty("创建人")
	private String createUserId;

	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	@ApiModelProperty("日程类型")
	private Integer type;

	@ApiModelProperty("日程名称")
	private String name;

	@ApiModelProperty("是否可进入")
	private Boolean enter;

}
