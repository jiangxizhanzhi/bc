package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;

/**
 * id列表请求体
 *
 * @author SN
 * @date 2024/11/13
 */
public class IdListReq {

    /**
     * id列表
     */
    @ApiModelProperty("ID列表")
	private List<String> ids;

	public List<String> getIds() {
		if (CollectionUtils.isEmpty(this.ids)) {
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		return this.ids;
	}
}
