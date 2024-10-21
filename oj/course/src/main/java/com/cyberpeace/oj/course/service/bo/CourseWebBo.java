package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("前端课程基本信息")
public class CourseWebBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("课程编号")
	private String courseNum;

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("课程方向")
	private String categoryId;

	@ApiModelProperty("课程方向名称")
	private String directionName;

	@ApiModelProperty("课程海报")
	private String logo;

	@ApiModelProperty("课程难度")
	private Integer difficulty;

	@ApiModelProperty("课程创建人")
	private String createUserId;

	@ApiModelProperty("创建人名称")
	private String createUserName;

	@ApiModelProperty("讲师")
	private String lecturer;

	@ApiModelProperty("学分")
	private Integer score;

	@ApiModelProperty("课程类型")
	private Integer courseType;

	@ApiModelProperty("课程类型")
	private Integer ebook;

	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("岗位")
	private Integer role;

}
