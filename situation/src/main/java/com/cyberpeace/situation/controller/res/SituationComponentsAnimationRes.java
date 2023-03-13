package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class SituationComponentsAnimationRes extends SituationComponentsAnimationAddReq {

	@ApiModelProperty()
	private String resourceId;

	@ApiModelProperty("组件名称")
	private String chartName;

	@ApiModelProperty("创建时间")
	private String creatTime;

	@ApiModelProperty("创建者")
	private String creator;

}
