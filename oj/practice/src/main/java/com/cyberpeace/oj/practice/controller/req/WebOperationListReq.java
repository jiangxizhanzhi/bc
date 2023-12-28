package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebOperationListReq extends PageReqBase {

	@ApiModelProperty("习题集")
	private String practiceSetId;

	@ApiModelProperty("是否解出")
	private Boolean solved;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("来源")
	private String source;

	@ApiModelProperty("题型")
	private Integer category;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}
}
