package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 实践设置更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeSetAddReq
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class PracticeSetUpdateReq extends PracticeSetAddReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源编号", required = true)
	@NotBlank(groups = { Update.class })
	private String resourceId;
}
