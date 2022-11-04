package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class TwoDimensionalComponentsUpdateReq extends TwoDimensionalComponentsAddReq {

	@NotBlank(groups = { Update.class },
			message = SituationErrorCode.RESOURCE_ID_EMPTY_ERROR)
	private String resourceId;

}
