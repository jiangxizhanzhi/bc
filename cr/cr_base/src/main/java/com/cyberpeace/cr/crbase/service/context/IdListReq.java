package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;

public class IdListReq {

	@ApiModelProperty("ID列表")
	private List<String> ids;

	public List<String> getIds() {
		if (CollectionUtils.isEmpty(this.ids)) {
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		return this.ids;
	}
}
