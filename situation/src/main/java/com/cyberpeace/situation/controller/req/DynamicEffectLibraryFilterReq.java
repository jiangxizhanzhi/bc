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
public class DynamicEffectLibraryFilterReq implements Serializable {

	@ApiModelProperty(value = "动效名称")
	private String name;

	@ApiModelProperty(value = "动效类型")
	private String category;

	@ApiModelProperty(value = "源节点动效文件")
	private String sourceNodeDynamicFile;

	@ApiModelProperty(value = "目标节点动效文件")
	private String targetNodeDynamicFile;

	@ApiModelProperty("聚焦")
	private String focus;

	@ApiModelProperty("颜色")
	private String color;

	@ApiModelProperty("线形")
	private String line;

	@ApiModelProperty("线形大小")
	private String lineSize;

}
