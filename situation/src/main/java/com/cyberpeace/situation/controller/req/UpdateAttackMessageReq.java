package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新攻击消息请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see AttackMessageReq
 * @since 1.0.0
 */
@Data
public class UpdateAttackMessageReq extends AttackMessageReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

}
