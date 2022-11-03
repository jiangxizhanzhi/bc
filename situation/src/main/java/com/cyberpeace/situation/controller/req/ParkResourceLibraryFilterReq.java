package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class ParkResourceLibraryFilterReq implements Serializable {

	@ApiModelProperty(value = "组件名称")
	private String name;

	@ApiModelProperty(value = "图表类型")
	private String parkResourceLibraryCategory;

}
