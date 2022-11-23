package com.cyberpeace.bs.controller.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdListReq implements Serializable {

	@ApiModelProperty(value = "id列表")
	private List<String> ids;

	public List<String> getIds() {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(BsErrorCode.EMPTY_ID_COLL);
		}
		return ids;
	}
}
