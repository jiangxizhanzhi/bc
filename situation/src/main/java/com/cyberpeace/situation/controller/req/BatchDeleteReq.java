package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BatchDeleteReq implements Serializable {

	@ApiModelProperty("resource_id列表")
	List<String> ids;

	public List<String> getIds() {
		if (this.ids.isEmpty()) {
			throw new BusinessException(SituationErrorCode.RESOURCE_ID_EMPTY_ERROR);
		}
		return this.ids;
	}
}
