package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午4:34
 */

@Data
public class DynamicEffectMappingReq implements Serializable {

	@NotBlank(groups = { Create.class }, message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动效名称", required = true)
	private String name;

	@NotBlank(groups = { Create.class }, message = SituationErrorCode.TYPE_EMPTY_ERROR)
	@ApiModelProperty(value = "分靶场类型", required = true)
	private String category;

	@NotBlank(groups = { Create.class })
	@ApiModelProperty(value = "攻击类型", required = true)
	private String attackType;

	@NotBlank(groups = { Create.class },
			message = SituationErrorCode.SOURCE_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "源节点动效", required = true)
	private String sourceNodeDynamic;

	@NotBlank(groups = { Create.class },
			message = SituationErrorCode.TARGET_NODE_DYNAMIC_FILE_EMPTY_ERROR)
	@ApiModelProperty(value = "目标节点动效", required = true)
	private String targetNodeDynamic;

	@NotBlank(groups = { Create.class }, message = SituationErrorCode.FOCUS_EMPTY_ERROR)
	@ApiModelProperty(value = "聚焦", required = true)
	private String focus;

	@NotBlank(groups = { Create.class }, message = SituationErrorCode.COLOR_EMPTY_ERROR)
	@ApiModelProperty(value = "颜色", required = true)
	private String color;

	@NotBlank(groups = { Create.class }, message = SituationErrorCode.LINE_EMPTY_ERROR)
	@ApiModelProperty(value = "线形", required = true)
	private String line;

	@NotNull(groups = { Create.class }, message = SituationErrorCode.LINE_SIZE_DOC_EMPTY_ERROR)
	@ApiModelProperty(value = "线形大小", required = true)
	private Integer lineSize;

	public void setFocus(String focus) {
		this.focus = "1".equals(focus) ? "true" : "false";
	}

}
