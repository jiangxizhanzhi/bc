package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 训练按学员统计
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TrainingPersonalStatistics implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "学员id")
	private String userId;

	/**
	 * 总数得分
	 */
	@ApiModelProperty(value = "学员总分")
	private Integer totalScore;
}
