package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效映射请求
 *
 * @Author xuxz
 * @Date 26/11/2022 下午4:34
 */

@Data
public class DynamicEffectMappingReq implements Serializable {

	/**
	 * 动效名称
	 */
	@NotBlank(groups = { Create.class }, message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动效名称", required = true)
	private String name;

	/**
	 * 分靶场类型
	 */
	@NotBlank(groups = { Create.class }, message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "分靶场类型", required = true)
	private String category;

	/**
	 * 攻击类型
	 */
	@NotBlank(groups = { Create.class })
	@ApiModelProperty(value = "攻击类型", required = true)
	private String attackType;

	/**
	 * 源节点动效
	 */
	@NotBlank(groups = { Create.class },
			message = SituationErrorCode.SOURCE_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "源节点动效", required = true)
	private String sourceNodeDynamic;

	/**
	 * 目标节点动效
	 */
	@NotBlank(groups = { Create.class },
			message = SituationErrorCode.TARGET_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "目标节点动效", required = true)
	private String targetNodeDynamic;

	/**
	 * 聚焦
	 */
	@NotBlank(groups = { Create.class }, message = SituationErrorCode.FOCUS_EMPTY_ERROR)
	@ApiModelProperty(value = "聚焦", required = true)
	private String focus;

	/**
	 * 颜色
	 */
	@NotBlank(groups = { Create.class }, message = SituationErrorCode.COLOR_EMPTY_ERROR)
	@ApiModelProperty(value = "颜色", required = true)
	private String color;

	/**
	 * 线形
	 */
	@NotBlank(groups = { Create.class }, message = SituationErrorCode.LINE_EMPTY_ERROR)
	@ApiModelProperty(value = "线形", required = true)
	private String line;

	/**
	 * 线形大小
	 */
	@NotNull(groups = { Create.class }, message = SituationErrorCode.LINE_SIZE_DOC_EMPTY_ERROR)
	@ApiModelProperty(value = "线形大小", required = true)
	private Integer lineSize;

	/**
	 * 设置聚焦
	 *
	 * @param focus 聚焦
	 */
	public void setFocus(String focus) {

		// 如果聚焦值为1，则设置为true，否则设置为false
		this.focus = "1".equals(focus) ? "true" : "false";
	}

}
