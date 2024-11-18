package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 训练按学员统计
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class TrainingPersonalStatistics implements Serializable {

	/**
     * 用户id
     *
     * @see String
     */
	@ApiModelProperty(value = "学员id")
	private String userId;

	/**
     * 总分
     *
     * @see Integer
     */
	@ApiModelProperty(value = "学员总分")
	private Integer totalScore;
}
