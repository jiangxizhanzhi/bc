package com.cyberpeace.rtks.res;

import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import com.cyberpeace.rtks.controller.res.RelatedCourse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeRes {

	@ApiModelProperty("知识点id")
	private String resourceId;

	@ApiModelProperty("知识点类别id")
	private String categoryId;

	@ApiModelProperty("知识点类别")
	private String category;

	@ApiModelProperty("知识点名称")
	private String content;

	@ApiModelProperty("知识点关联课程字符串")
	private String courses;

	@ApiModelProperty("知识点课程权重")
	private Integer weight = 1;

	@ApiModelProperty("知识点所有课程总体进度")
	private Integer process;

	@ApiModelProperty("知识点关联课程")
	private List<RelatedCourse> courseList;

	@ApiModelProperty("建议学习时长")
	private Double suggestDuration = 0.0;

	@ApiModelProperty("知识点关联课程学习进度信息")
	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}