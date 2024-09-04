package com.cyberpeace.cs.service.context;

import java.util.List;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.Data;

@Data
public class IdListReq {

	private List<String> ids;

	public List<String> getIds() {
		if (null == this.ids || this.ids.isEmpty()) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return this.ids;
	}
}
