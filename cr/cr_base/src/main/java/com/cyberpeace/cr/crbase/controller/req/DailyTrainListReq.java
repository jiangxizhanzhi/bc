package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class DailyTrainListReq extends PageReqBase {

	@ApiModelProperty(value = "类型")
	private Integer type;

}

