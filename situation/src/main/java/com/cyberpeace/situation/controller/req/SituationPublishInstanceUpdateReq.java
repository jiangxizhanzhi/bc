package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏实例更新请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationPublishInstanceUpdateReq extends SituationPublishInstanceAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty()
	@NotBlank(groups = { Update.class }, message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;
}
