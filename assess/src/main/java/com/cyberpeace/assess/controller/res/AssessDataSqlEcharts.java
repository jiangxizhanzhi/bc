package com.cyberpeace.assess.controller.res;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估数据
 * @TableName assess_data
 */
@Data
public class AssessDataSqlEcharts implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty(value = "x轴标签")
	private List<String> dimensions;

	@ApiModelProperty(value = "y轴标签")
	private List<JSONObject> source;
}