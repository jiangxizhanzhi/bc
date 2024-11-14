package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 个人统计详情4口Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PersonalStatsDetail4MouthBo implements Serializable {

	/**
	 * 近30天日期
	 */
	@ApiModelProperty("近30天日期")
    private List<String> thirtyDays;

	/**
	 * 学习课程(实际数据为课时)
	 */
	@ApiModelProperty("学习课程(实际数据为课时)")
    private List<Integer> lessonLearnRecord;

	/**
	 * 完成报告
	 */
	@ApiModelProperty("完成报告")
    private List<Integer> reportFinishRecord;

	/**
	 * 报告平均分
	 */
	@ApiModelProperty("报告平均分")
    private List<Integer> averageScore;

}
