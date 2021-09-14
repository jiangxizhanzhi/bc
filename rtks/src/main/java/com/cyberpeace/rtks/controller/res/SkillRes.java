package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillRes implements Serializable {

	@ApiModelProperty("技能id")
	private String resourceId;

	@ApiModelProperty("技能体系id")
	private String systemId;

	@ApiModelProperty("技能体系")
	private String system;

	@ApiModelProperty("技能类别id")
	private String categoryId;

	@ApiModelProperty("技能类别")
	private String category;

	@ApiModelProperty("技能名称")
	private String content;

	@ApiModelProperty("技能关联课程字符串")
	private String courses;

	@ApiModelProperty("技能课程权重")
	private Integer weight = 1;

	@ApiModelProperty("技能所有课程总体进度")
	private Integer process;

	@ApiModelProperty("技能关联课程")
	private List<RelatedCourse> courseList;

	@ApiModelProperty("技能关联课程学习进度信息")
	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}