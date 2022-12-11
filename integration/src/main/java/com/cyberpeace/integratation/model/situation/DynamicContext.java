package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicContext implements Serializable {

	@ApiModelProperty(value = "参数列表")
	private List<String> columnList;

	@ApiModelProperty(value = "内容标题")
	private String listName;

	@ApiModelProperty(value = "内容值列表")
	private List<List<String>> valueList;
}
