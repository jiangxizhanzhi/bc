package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeRes implements Serializable {

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
	private Integer weight;

	@ApiModelProperty("知识点所有课程总体进度")
	private Integer process;

	@ApiModelProperty("知识点关联课程")
	private List<RelatedCourse> courseList;

	@ApiModelProperty("知识点关联课程学习进度信息")
	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}