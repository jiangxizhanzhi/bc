package com.cyberpeace.cr.crbase.service.context.wiki;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdListReq implements Serializable {

	@ApiModelProperty(value = "id列表")
	List<String> ids;

	public List<String> getIds() {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		return ids;
	}
}
