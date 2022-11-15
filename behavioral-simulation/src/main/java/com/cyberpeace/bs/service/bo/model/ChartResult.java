package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

/**
 * 行为的时间分配和标签百分比
 * @author SN
 */
@Data
public class ChartResult implements Serializable {

	@ApiModelProperty(value = "行为列表")
	private List<Behavior> behaviorList;

	@ApiModelProperty(value = "横轴-时间（0~23时）")
	private List<String> xTime = Lists.newArrayList();

	@ApiModelProperty(value = "纵轴-行为数")
	private List<Integer> yNum = Lists.newArrayList();
}
