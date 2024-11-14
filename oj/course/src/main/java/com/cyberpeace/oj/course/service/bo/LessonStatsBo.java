package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课时统计Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class LessonStatsBo implements Serializable {
	/**
	 * 课时resource_id
	 */
	@ApiModelProperty("课时resource_id")
    private String resourceId;

	/**
	 * 课程resource_id
	 */
	@ApiModelProperty("课程resource_id")
    private String courseId;

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
    private String name;

	/**
	 * 课时类型
	 */
	@ApiModelProperty("课时类型")
    private Integer type;

	/**
	 * 课时难度
	 */
	@ApiModelProperty("课时难度")
    private Integer difficulty;

	/**
	 * 学习人数
	 */
	@ApiModelProperty("学习人数")
    private Integer learningCount;

	/**
	 * 提交报告数量
	 */
	@ApiModelProperty("提交报告数量")
    private Integer reportCount;

	/**
	 * 完成练习数
	 */
	@ApiModelProperty("完成练习数")
    private Integer finishPracticeCount;

}
