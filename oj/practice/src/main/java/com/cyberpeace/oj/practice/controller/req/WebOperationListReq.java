package com.cyberpeace.oj.practice.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebOperationListReq extends PageReqBase {

	@ApiModelProperty("方向")
	private String categoryId;

	@ApiModelProperty("习题集")
	private String practiceSetId;

	@ApiModelProperty("是否解出")
	private Boolean solved;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("知识点，用逗号隔开多个知识点")
	private List<String> knowledge;

	@ApiModelProperty("来源")
	private String source;

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}
}
