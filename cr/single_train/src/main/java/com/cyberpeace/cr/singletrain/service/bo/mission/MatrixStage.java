package com.cyberpeace.cr.singletrain.service.bo.mission;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 矩阵阶段
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MatrixStage implements Serializable {

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stageName;

	/**
	 * 任务
	 *
	 * @see List<MatrixMission>
	 */
	@ApiModelProperty(value = "任务")
	private List<MatrixMission> missions = Lists.newArrayList();
}
