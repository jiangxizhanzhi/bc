package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二维组件新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class TwoDimensionalComponentsAddReq implements Serializable {

	/**
	 * 组件名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "组件名称", required = true)
	private String name;

	/**
	 * 图表类型
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "图表类型", required = true)
	private String type;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 配置
	 */
	@ApiModelProperty(value = "配置")
	private String config;

	/**
	 * 缩略图
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	/**
	 * 组件代码
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.COMPONENT_CODE_EMPTY_ERROR)
	@ApiModelProperty(value = "组件代码", required = true)
	private String componentCode;

}
