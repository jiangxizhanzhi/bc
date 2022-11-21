package com.cyberpeace.assess.controller.res;

import com.cyberpeace.assess.constant.AssessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class AssessCaseListReq implements Serializable {


	@ApiModelProperty(value = "评估案例名称")
	private String name;

	@ApiModelProperty(value = "概述")
	private String overview;

	@ApiModelProperty(value = "封面")
	private String cover;

	@ApiModelProperty(value = "标签")
	private String tag;

	@ApiModelProperty(value = "最后更新时间")
	private String updateTime;
}
