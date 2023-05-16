package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效库更新请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DynamicEffectUpdateReq extends DynamicEffectAddReq {

	/**
	 * 资源ID
	 */
	@ApiModelProperty()
	@NotBlank(groups = { Update.class }, message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;

}
