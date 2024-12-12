package com.cyberpeace.cr.crbase.service.context.manual;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 手动载物台 BO
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
