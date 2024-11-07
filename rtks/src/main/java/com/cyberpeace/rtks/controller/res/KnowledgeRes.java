package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class KnowledgeRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("知识点id")
	private String resourceId;

	/**
	 * 类别id
	 */
	@ApiModelProperty("知识点类别id")
	private String categoryId;

	/**
	 * 类别
	 */
	@ApiModelProperty("知识点类别")
	private String category;

	/**
	 * 内容
	 */
	@ApiModelProperty("知识点名称")
	private String content;

	/**
	 * 课程
	 */
	@ApiModelProperty("知识点关联课程字符串")
	private String courses;

	/**
	 * 重量
	 */
	@ApiModelProperty("知识点课程权重")
	private Integer weight = 1;

	/**
	 * 过程
	 */
	@ApiModelProperty("知识点所有课程总体进度")
	private Integer process;

	/**
	 * 课程列表
	 */
	@ApiModelProperty("知识点关联课程")
	private List<RelatedCourse> courseList;

	/**
	 * 建议持续时间
	 */
	@ApiModelProperty("建议学习时长")
	private Double suggestDuration = 0.0;

	/**
	 * 课程学习过程 BO 列表
	 */
	@ApiModelProperty("知识点关联课程学习进度信息")
	private List<CourseStudyProcessBo> courseStudyProcessBoList;

}