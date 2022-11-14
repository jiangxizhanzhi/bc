package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;


@Data
public class TwoDimensionalComponentsAddReq implements Serializable {

	@NotBlank(groups = {Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "组件名称", required = true)
	private String name;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "图表类型", required = true)
	private String type;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "配置")
	private String config;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.COMPONENT_CODE_EMPTY_ERROR)
	@ApiModelProperty(value = "组件代码", required = true)
	private String componentCode;

}
