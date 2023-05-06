package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效库新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DynamicEffectLibraryAddReq implements Serializable {

	/**
	 * 动效名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动效名称", required = true)
	private String name;

	/**
	 * 动效类型
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "动效类型", required = true)
	private String category;

	/**
	 * 缩略图
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	/**
	 * 源节点动效文件
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.SOURCE_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "源节点动效文件", required = true)
	private String sourceNodeDynamicFile;

}
