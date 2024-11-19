package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 百分之结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("进度数据")
public class PercentRes implements Serializable {

	/**
	 * x轴，统计项
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("x轴，统计项")
    private List<String> xNames;

	/**
	 * y轴，数值
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("y轴，数值")
    private List<Integer> yPercents;

}
