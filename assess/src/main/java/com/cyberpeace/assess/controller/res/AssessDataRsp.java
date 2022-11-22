package com.cyberpeace.assess.controller.res;

import java.util.List;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估数据
 * @TableName assess_data
 */
@Data
public class AssessDataRsp extends BaseTable {

	@ApiModelProperty(value = "名称", required = true)
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty("标签")
	private List<String> tag;

	@ApiModelProperty("封面")
	private String logo;

	@ApiModelProperty("创建者")
	private String createUser;

	@ApiModelProperty("评估数据类型。1：输入，2：结果")
	private Integer type;

	@ApiModelProperty("输入数据集类型。1：mysql，2：excel")
	private Integer inputType;

	@ApiModelProperty("数据集路径")
	private String inputData;

	@ApiModelProperty("结果路径")
	private String resultData;

}