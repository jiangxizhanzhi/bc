package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 园区资源库新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ParkResourceLibraryAddReq implements Serializable {

	/**
	 * 园区资源库名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "园区资源库名称", required = true)
	private String name;

	/**
	 * 资源类型
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "资源类型", required = true)
	private String parkResourceLibraryCategory;

	/**
	 * 缩略图
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	/**
	 * 资源url
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.RESOURCE_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "资源url", required = true)
	private String resourceUrl;

}
