package com.cyberpeace.common.model;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import io.swagger.annotations.ApiModelProperty;

public class IdListReq {

	@ApiModelProperty("ID列表")
	private List<String> ids;

	public List<String> getIds() {
		if (CollectionUtils.isEmpty(this.ids)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return this.ids;
	}
}
