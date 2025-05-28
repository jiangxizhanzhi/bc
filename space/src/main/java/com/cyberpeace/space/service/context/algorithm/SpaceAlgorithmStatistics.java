package com.cyberpeace.space.service.context.algorithm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space 算法统计
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmStatistics {

	/**
	 * 专门知识
	 */
	@ApiModelProperty(value = "专业能力评估算法")
	private Integer expertise;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位能力评估算法")
	private Integer role;

	/**
	 * 个人
	 */
	@ApiModelProperty(value = "个人能力评估算法")
	private Integer individual;

	/**
	 * 团队
	 */
	@ApiModelProperty(value = "团队能力评估算法")
	private Integer team;

}
