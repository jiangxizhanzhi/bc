package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量删除请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class BatchDeleteReq implements Serializable {

	/**
	 * 资源ID列表
	 */
	@ApiModelProperty("资源ID列表")
	List<String> ids;

	/**
	 * 提取资源ID列表
	 *
	 * @return {@code List<String>}
	 */
	public List<String> getIds() {

		// 如果ids为空，抛出异常
		if (this.ids.isEmpty()) {
			throw new BusinessException(SituationErrorCode.RESOURCE_ID_EMPTY_ERROR);
		}
		return this.ids;
	}
}
