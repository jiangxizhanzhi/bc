package com.cyberpeace.rtks.res;

import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import com.cyberpeace.rtks.controller.res.RelatedCourse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识点结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class KnowledgeRes {

	/**
	 * 知识点id
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点id")
	private String resourceId;

	/**
	 * 知识点类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点类别id")
	private String categoryId;

	/**
	 * 知识点类别
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点类别")
	private String category;

	/**
	 * 知识点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点名称")
	private String content;

	/**
	 * 知识点关联课程字符串
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点关联课程字符串")
	private String courses;

	/**
	 * 知识点课程权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty("知识点课程权重")
	private Integer weight = 1;

	/**
	 * 知识点所有课程总体进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("知识点所有课程总体进度")
	private Integer process;

	/**
	 * 知识点关联课程
	 *
	 * @see List<RelatedCourse>
	 */
	@ApiModelProperty("知识点关联课程")
	private List<RelatedCourse> courseList;

	/**
	 * 建议学习时长
	 *
	 * @see Double
	 */
	@ApiModelProperty("建议学习时长")
	private Double suggestDuration = 0.0;

	/**
	 * 知识点关联课程学习进度信息
	 *
	 * @see List<CourseStudyProcessBo>
	 */
	@ApiModelProperty("知识点关联课程学习进度信息")
	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}