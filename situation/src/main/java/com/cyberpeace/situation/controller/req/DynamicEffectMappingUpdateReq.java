package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;

import lombok.Data;

/**
 * 动效映射更新请求
 *
 * @Author xuxz
 * @Date 26/11/2022 下午5:15
 */
@Data
public class DynamicEffectMappingUpdateReq extends DynamicEffectMappingReq {

	/**
	 * 资源ID
	 */
	@NotBlank(groups = { Update.class }, message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;
}
