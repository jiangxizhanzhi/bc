package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.service.bo.behavior.Behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
	private List<YObject> yNum = Lists.newArrayList();

	@ApiModelProperty(value = "饼状图")
	private List<NameValue> pieChart= Lists.newArrayList();

	public void addY(Integer num, List<String> labels) {
		yNum.add(new YObject(num, labels));
	}
}

@Data
class YObject implements Serializable {

	@ApiModelProperty(value = "纵轴-行为数")
	private Integer num;

	@ApiModelProperty(value = "纵轴-名称")
	private List<String> labels;

	YObject(Integer num, List<String> labels) {
		this.num = num;
		this.labels = labels;
	}
}