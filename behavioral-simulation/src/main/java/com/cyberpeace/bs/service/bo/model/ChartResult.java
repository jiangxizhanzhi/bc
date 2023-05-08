package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.service.bo.behavior.Behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图表结果
 * 行为的时间分配和标签百分比
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class ChartResult implements Serializable {

	/**
	 * 行为列表
	 * 将行为设置时间后返回
	 */
	@ApiModelProperty(value = "行为列表")
	private List<Behavior> behaviorList;

	/**
	 * 横轴-时间（0~23时）
	 */
	@ApiModelProperty(value = "横轴-时间（0~23时）")
	private List<String> xTime = Lists.newArrayList();

	/**
	 * 纵轴-行为数
	 */
	@ApiModelProperty(value = "纵轴-行为数")
	private List<YObject> yNum = Lists.newArrayList();

	/**
	 * 饼状图
	 */
	@ApiModelProperty(value = "饼状图")
	private List<NameValue> pieChart= Lists.newArrayList();

	/**
	 * 添加纵轴数据
	 *
	 * @param num    数量
	 * @param labels 标签
	 */
	public void addY(Integer num, List<String> labels) {
		yNum.add(new YObject(num, labels));
	}
}

/**
 * 纵轴数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class YObject implements Serializable {

	/**
	 * 纵轴-行为数
	 */
	@ApiModelProperty(value = "纵轴-行为数")
	private Integer num;

	/**
	 * 标签
	 */
	@ApiModelProperty(value = "纵轴-名称")
	private List<String> labels;

	YObject(Integer num, List<String> labels) {
		this.num = num;
		this.labels = labels;
	}
}