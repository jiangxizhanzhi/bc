package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 练习设置更新请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeSetUpdateReq extends PracticeSetAddReq {

	/**
	 * 资源编号
	 */
	@ApiModelProperty(value = "资源编号", required = true)
	@NotBlank(groups = { Update.class })
	private String resourceId;
}
