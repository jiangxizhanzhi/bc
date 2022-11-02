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

	@NotNull(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "图表类型", required = true)
	private Integer type;

	@ApiModelProperty(value = "描述")
	private String description;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.COMPONENT_CODE_EMPTY_ERROR)
	@ApiModelProperty(value = "组件代码", required = true)
	private String componentCode;

	@NotBlank(groups = { Update.class },
			message = SituationErrorCode.RESOURCE_ID_EMPTY_ERROR)
	private String resourceId;

}
