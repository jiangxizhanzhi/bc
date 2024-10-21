package com.cyberpeace.space.service.context;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Data
public class IdListReq {

	@ApiModelProperty("ID列表")
	private List<String> ids;

	public List<String> getIds() {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return this.ids;
	}
}
