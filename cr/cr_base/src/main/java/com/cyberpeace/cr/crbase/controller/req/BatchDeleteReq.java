package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Data
@Slf4j
public class BatchDeleteReq implements Serializable {

	@ApiModelProperty("resource_id列表")
	List<String> ids;

	public List<String> getIds() {
		if (this.ids == null || this.ids.size() == 0) {
			log.error("Resource id missing! templateModifyReq = [{}]", JsonUtil.toJsonString(ids));
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		return this.ids;
	}
}
