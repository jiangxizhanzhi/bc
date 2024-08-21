package com.cyberpeace.cr.teamtrain.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.Data;

@Data
public class TeamTrainTemplateAddReq extends TeamTrainTemplateReq {

	public void validate() {
		if (StringUtils.isBlank(this.getVehicleFrameId())) {
			throw new BusinessException(ErrorCode.EMPTY_VEHICLE_FRAME_ID);
		}
	}

}
