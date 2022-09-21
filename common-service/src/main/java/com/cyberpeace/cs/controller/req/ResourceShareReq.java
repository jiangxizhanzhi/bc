package com.cyberpeace.cs.controller.req;

import java.util.List;

import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.baseinterface.EnumValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("资源授权操作")
public class ResourceShareReq {

	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	@ApiModelProperty("授权人编号集合")
	private List<String> operationAuthIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer operationAuthType;

}
