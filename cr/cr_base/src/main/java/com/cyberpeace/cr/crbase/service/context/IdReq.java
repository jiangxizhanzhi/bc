package com.cyberpeace.cr.crbase.service.context;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * id请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class IdReq {

	/**
	 * id
	 */
	@ApiModelProperty("ID")
	private String id;

	public String getId() {
		if (StringUtils.isBlank(this.id)) {
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		return this.id;
	}
}
