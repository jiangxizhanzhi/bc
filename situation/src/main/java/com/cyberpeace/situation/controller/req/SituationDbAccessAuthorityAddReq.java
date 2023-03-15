package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class SituationDbAccessAuthorityAddReq implements Serializable {

	@NotBlank(groups = {Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动画名称", required = true)
	private String name;

	@ApiModelProperty(value = "数据类型")
	private String dbType;

	@ApiModelProperty(value = "系统ip")
	private String sysIp;

}
