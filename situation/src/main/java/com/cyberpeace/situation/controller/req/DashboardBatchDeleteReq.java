package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardBatchDeleteReq implements Serializable {

	@ApiModelProperty("大屏ID列表")
	List<String> ids;

	public List<String> getIds() {
		if (this.ids.isEmpty()) {
			throw new BusinessException(SituationErrorCode.RESOURCE_ID_EMPTY_ERROR);
		}
		return this.ids;
	}

}
