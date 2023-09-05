package com.cyberpeace.oj.ojbase.service.bo.check;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckPoint implements Serializable {

	@ApiModelProperty(value = "检查点名称")
	private String knowledgeName;

	@ApiModelProperty(value = "检查点时间")
	private String knowledgeTime;

	@ApiModelProperty(value = "检查点理论题")
	private List<CheckPointQuestion> knowledgeAnswerList = Lists.newArrayList();
}
