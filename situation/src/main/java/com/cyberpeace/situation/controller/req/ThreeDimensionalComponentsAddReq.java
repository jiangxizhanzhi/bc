package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 三维组件新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ThreeDimensionalComponentsAddReq implements Serializable {

	/**
	 * 组件名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "组件名称", required = true)
	private String name;

	/**
	 * 组件类型
	 */
	@NotNull(groups = { Create.class, Update.class }, message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "组件类型", required = true)
	private String threeDimensionalComponentsCategory;

	/**
	 * 缩略图
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	/**
	 * 图表标准文件
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.CHART_STANDARD_DOC_EMPTY_ERROR)
	@ApiModelProperty(value = "图表标准文件", required = true)
	private String chartStandardDocument;
}
