package com.cyberpeace.iov.service.bo;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdsReq {

	@ApiModelProperty("ID列表")
	private List<Integer> ids;

	public List<Integer> getIds() {
		if (CollectionUtils.isEmpty(this.ids)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return this.ids;
	}
}
