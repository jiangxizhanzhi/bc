package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 训练按学员统计
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TrainingPersonalStatistics implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "学员id")
	private String userId;

	/**
	 * 总分
	 */
	@ApiModelProperty(value = "学员总分")
	private Integer totalScore;
}
