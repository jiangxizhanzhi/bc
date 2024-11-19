package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共统计Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PublicStatsBo implements Serializable {
	/**
	 * 完成课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成课时数量")
    private Integer lessonRecordCount = 0;

	/**
	 * 完成实验数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成实验数量")
    private Integer finishReportCount = 0;

	/**
	 * 完成练习数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成练习数量")
    private Integer finishPracticeCount = 0;

	/**
	 * 完成考试数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成考试数")
    private Integer finishExamCount = 0;

	/**
	 * 实验平均得分
	 */
	@ApiModelProperty("实验平均得分")
    private float reportAverage = 0;

}
