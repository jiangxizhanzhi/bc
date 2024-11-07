package com.cyberpeace.space.service.context.tactic;

import lombok.Data;

/**
 * 攻击修改请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AttackModifyReq extends AttackAddReq {

	/**
	 * 资源id
	 */
	private String resourceId;
}
