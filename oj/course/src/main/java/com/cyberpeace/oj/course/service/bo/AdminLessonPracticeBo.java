package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理课时练习Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class AdminLessonPracticeBo implements Serializable {

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
    private String answer;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
    private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
    private String content;

	/**
	 * 选项
	 */
	@ApiModelProperty("选项")
    private String option;

	/**
	 * 方向名称
	 */
	@ApiModelProperty("方向名称")
    private String directionName;

	/**
	 * 习题类型
	 */
	@ApiModelProperty("习题类型")
    private Integer practiceType;

	/**
	 * 题型
	 */
	@ApiModelProperty("题型")
    private Integer questionType;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
    private Integer difficulty;

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
    private String resourceId;

	/**
	 * 快照编号
	 */
	@ApiModelProperty("快照编号")
    private String refId;

	/**
	 * 分值
	 */
	@ApiModelProperty("分值")
    private Double score;

}
