package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户统计排Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class UserStatsRankBo implements Serializable {
	/**
	 * 课时学习用户排名
	 */
	@ApiModelProperty("课时学习用户排名")
    private List<String> lessonRecordUser;

	/**
	 * 课时学习数量
	 */
	@ApiModelProperty("课时学习数量")
    private List<Integer> lessonRecordCount;

	/**
	 * 完成实验排名
	 */
	@ApiModelProperty("完成实验排名")
    private List<String> finishReportUser;

	/**
	 * 完成实验数量
	 */
	@ApiModelProperty("完成实验数量")
    private List<Integer> finishReportCount;

	/**
	 * 完成课程练习排名
	 */
	@ApiModelProperty("完成课程练习排名")
    private List<String> finishPracticeUser;

	/**
	 * 完成课程练习数量
	 */
	@ApiModelProperty("完成课程练习数量")
    private List<Integer> finishPracticeCount;

	/**
	 * 参加考试次数排名
	 */
	@ApiModelProperty("参加考试次数排名")
    private List<String> attendExamUser;

	/**
	 * 参加考试次数
	 */
	@ApiModelProperty("参加考试次数")
    private List<Integer> attendExamCount;
}
