package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课堂实践详情博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeDetailBo implements Serializable {

	/**
	 * 课名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 提交用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("学员名字")
	private String submitUserName;

	/**
	 * 准确性
	 *
	 * @see String
	 */
	@ApiModelProperty("准确率")
	private String accuracy;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题目数量")
	private Integer practiceCount;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评级")
	private Integer level;

	/**
	 * 执业名单
	 *
	 * @see Map<String, LessonPracticeListBo>
	 */
	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;
}
