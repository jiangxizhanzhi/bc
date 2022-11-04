package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class ParkResourceLibraryAddReq implements Serializable {

	@NotBlank(groups = {Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "园区资源库名称", required = true)
	private String name;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "资源类型", required = true)
	private String parkResourceLibraryCategory;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.RESOURCE_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "资源文件", required = true)
	private String resourceFile;

}
