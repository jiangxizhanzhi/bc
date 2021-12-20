package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BatchDeleteReq implements Serializable {

	@ApiModelProperty("resource_id列表")
	List<String> ids;

	public List<String> getIds() {
		if (this.ids == null || this.ids.size() == 0) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		return this.ids;
	}
}
