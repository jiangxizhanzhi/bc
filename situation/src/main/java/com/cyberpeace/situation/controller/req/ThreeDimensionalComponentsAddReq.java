package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;


@Data
public class ThreeDimensionalComponentsAddReq implements Serializable {

	@NotBlank(groups = {Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "组件名称", required = true)
	private String name;

	@NotNull(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "组件类型", required = true)
	private String threeDimensionalComponentsCategory;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.CHART_STANDARD_DOC_EMPTY_ERROR)
	@ApiModelProperty(value = "图表标准文件", required = true)
	private File chartStandardDocument;

	@ApiModelProperty()
	@NotBlank(groups = { Update.class },
			message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;

}
