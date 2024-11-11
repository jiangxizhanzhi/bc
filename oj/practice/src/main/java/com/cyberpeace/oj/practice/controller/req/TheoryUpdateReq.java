package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 理论更新请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("理论基础练习")
public class TheoryUpdateReq extends TheoryAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "ID", required = true)
	@NotBlank(groups = { Update.class })
	private String resourceId;

}
