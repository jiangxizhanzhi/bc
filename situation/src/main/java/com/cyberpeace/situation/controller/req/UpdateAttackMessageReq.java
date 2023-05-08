package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新攻击消息请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class UpdateAttackMessageReq extends AttackMessageReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

}
