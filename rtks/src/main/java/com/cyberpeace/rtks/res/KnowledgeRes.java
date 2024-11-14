package com.cyberpeace.rtks.res;

import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import com.cyberpeace.rtks.controller.res.RelatedCourse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeRes {

	/**
	 * 知识点id
	 */
	@ApiModelProperty("知识点id")
	private String resourceId;

	/**
	 * 知识点类别id
	 */
	@ApiModelProperty("知识点类别id")
	private String categoryId;

	/**
	 * 知识点类别
	 */
	@ApiModelProperty("知识点类别")
	private String category;

	/**
	 * 知识点名称
	 */
	@ApiModelProperty("知识点名称")
	private String content;

	/**
	 * 知识点关联课程字符串
	 */
	@ApiModelProperty("知识点关联课程字符串")
	private String courses;

	/**
	 * 知识点课程权重
	 */
	@ApiModelProperty("知识点课程权重")
	private Integer weight = 1;

	/**
	 * 知识点所有课程总体进度
	 */
	@ApiModelProperty("知识点所有课程总体进度")
	private Integer process;

	/**
	 * 知识点关联课程
	 */
	@ApiModelProperty("知识点关联课程")
	private List<RelatedCourse> courseList;

	/**
	 * 建议学习时长
	 */
	@ApiModelProperty("建议学习时长")
	private Double suggestDuration = 0.0;

	/**
	 * 知识点关联课程学习进度信息
	 */
	@ApiModelProperty("知识点关联课程学习进度信息")
	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}