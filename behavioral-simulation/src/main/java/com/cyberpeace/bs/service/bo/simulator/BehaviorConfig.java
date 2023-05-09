package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

/**
 * 行为配置
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class BehaviorConfig implements Serializable {

	/**
	 * 行为id
	 */
	@ApiModelProperty(value = "行为id")
	private String behaviorId;

	/**
	 * 变量
	 */
	@ApiModelProperty(value = "行为变量列表")
	private List<VariableBo> variables = Lists.newArrayList();
}
