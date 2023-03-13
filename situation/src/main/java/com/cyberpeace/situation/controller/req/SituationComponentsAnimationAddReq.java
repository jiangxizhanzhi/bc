package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class SituationComponentsAnimationAddReq implements Serializable {

	@NotBlank(groups = {Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动画名称", required = true)
	private String name;

	@ApiModelProperty(value = "二维组件id")
	private String chartId;

	@ApiModelProperty(value = "动画描述")
	private String description;

	@ApiModelProperty(value = "配置")
	private String config;

}
