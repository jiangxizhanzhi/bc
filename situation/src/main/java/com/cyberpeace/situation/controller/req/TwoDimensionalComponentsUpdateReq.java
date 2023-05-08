package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import lombok.Data;

/**
 * 二维组件更新请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class TwoDimensionalComponentsUpdateReq extends TwoDimensionalComponentsAddReq {

	/**
	 * 资源id
	 */
	@NotBlank(groups = { Update.class }, message = SituationErrorCode.RESOURCE_ID_EMPTY_ERROR)
	private String resourceId;

}
