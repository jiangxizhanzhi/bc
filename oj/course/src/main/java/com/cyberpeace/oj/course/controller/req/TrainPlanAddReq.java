package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanAddReq implements Serializable {

	@ApiModelProperty("资源名称")
	private String name;

	@ApiModelProperty("类型, 1=普通，2=连续")
	private Integer type;

	@ApiModelProperty("组织编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL)
	private String organizationId;
}
