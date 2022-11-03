package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AddCategoryReq implements Serializable {

	@ApiModelProperty("分组")
	@NotBlank(message = SituationErrorCode.NAME_EMPTY_ERROR)
	String name;

}
