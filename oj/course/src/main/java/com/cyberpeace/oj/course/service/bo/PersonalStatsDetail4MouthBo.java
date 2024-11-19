package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 月度个人统计详情Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PersonalStatsDetail4MouthBo implements Serializable {

	/**
	 * 近30天日期
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("近30天日期")
    private List<String> thirtyDays;

	/**
	 * 学习课程(实际数据为课时)
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("学习课程(实际数据为课时)")
    private List<Integer> lessonLearnRecord;

	/**
	 * 完成报告
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("完成报告")
    private List<Integer> reportFinishRecord;

	/**
	 * 报告平均分
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("报告平均分")
    private List<Integer> averageScore;

}
