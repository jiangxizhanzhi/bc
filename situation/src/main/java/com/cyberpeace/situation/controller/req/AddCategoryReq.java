package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增分组请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class AddCategoryReq implements Serializable {

	/**
	 * 分组
	 */
	@ApiModelProperty("分组")
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	String name;

	/**
	 * 资源ID
	 */
	@ApiModelProperty("资源ID")
	@NotBlank(groups = { Update.class }, message = SituationErrorCode.RESOURCE_ID_EMPTY_ERROR)
	String resourceId;

}
