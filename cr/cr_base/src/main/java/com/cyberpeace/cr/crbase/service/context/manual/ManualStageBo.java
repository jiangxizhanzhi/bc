package com.cyberpeace.cr.crbase.service.context.manual;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手动载物台 BO
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ManualStageBo implements Serializable {

	/**
	 * 阶段名称
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stageName;

	/**
	 * 任务
	 */
	@ApiModelProperty(value = "任务")
	private List<ManualMissionBo> missions = Lists.newArrayList();
}
