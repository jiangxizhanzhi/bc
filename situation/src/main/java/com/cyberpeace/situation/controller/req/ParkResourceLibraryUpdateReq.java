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
public class ParkResourceLibraryUpdateReq extends ParkResourceLibraryAddReq {

	@ApiModelProperty()
	@NotBlank(groups = { Update.class },
			message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;

}
