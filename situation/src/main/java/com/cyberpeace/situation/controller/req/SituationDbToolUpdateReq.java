package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库工具更新请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationDbToolUpdateReq extends SituationDbToolAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@NotBlank(groups = { Update.class }, message = SituationErrorCode.RESOURCE_ID_EMPTY_ERROR)
	String resourceId;

}
