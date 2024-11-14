package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("进度数据")
public class PercentRes implements Serializable {

	/**
	 * x轴，统计项
	 */
    @ApiModelProperty("x轴，统计项")
    private List<String> xNames;

	/**
	 * y轴，数值
	 */
    @ApiModelProperty("y轴，数值")
    private List<Integer> yPercents;

}
