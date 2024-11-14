package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课堂练习详情Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeDetailBo implements Serializable {

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 学员名字
	 */
	@ApiModelProperty("学员名字")
	private String submitUserName;

	/**
	 * 准确率
	 */
	@ApiModelProperty("准确率")
	private String accuracy;

	/**
	 * 题目数量
	 */
	@ApiModelProperty("题目数量")
	private Integer practiceCount;

	/**
	 * 评级
	 */
	@ApiModelProperty("评级")
	private Integer level;

	/**
	 * 课后练习题信息
	 */
	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;
}
