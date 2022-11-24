package com.cyberpeace.assess.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AssessDataAddReq implements Serializable {

	@ApiModelProperty(value = "名称", required = true)
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty("标签")
	private List<String> tag;

	@ApiModelProperty("封面")
	private String logo;

	@ApiModelProperty("评估数据类型。1：输入，2：结果")
	private Integer type;

	@ApiModelProperty("输入数据集类型。1：mysql，2：excel")
	private Integer inputType;

	@ApiModelProperty("上传文件名称")
	private String fileName;

	@ApiModelProperty("数据集路径")
	private String inputData;

	@ApiModelProperty("结果路径")
	private String resultData;
}
