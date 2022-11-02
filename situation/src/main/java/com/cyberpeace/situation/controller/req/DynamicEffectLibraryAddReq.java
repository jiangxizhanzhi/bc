package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class DynamicEffectLibraryAddReq implements Serializable {

	@NotBlank(groups = {Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动效名称", required = true)
	private String name;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "动效类型", required = true)
	private String dynamicEffectsLibraryCategory;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.SOURCE_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "源节点动效文件", required = true)
	private String sourceNodeDynamicFile;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TARGET_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "目标节点动效文件", required = true)
	private String targetNodeDynamicFile;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.FOCUS_EMPTY_ERROR)
	@ApiModelProperty(value = "聚焦", required = true)
	private String focus;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.COLOR_EMPTY_ERROR)
	@ApiModelProperty(value = "颜色", required = true)
	private String color;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.LINE_EMPTY_ERROR)
	@ApiModelProperty(value = "线形", required = true)
	private String line;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.LINE_SIZE_DOC_EMPTY_ERROR)
	@ApiModelProperty(value = "线形大小", required = true)
	private String lineSize;

	@ApiModelProperty()
	@NotBlank(groups = { Update.class },
			message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;

}
