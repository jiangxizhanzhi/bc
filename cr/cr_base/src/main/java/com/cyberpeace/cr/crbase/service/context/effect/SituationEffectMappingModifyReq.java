package com.cyberpeace.cr.crbase.service.context.effect;

import lombok.Data;

/**
 * 态势动效映射修改请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectMappingModifyReq extends SituationEffectMappingAddReq {

	/**
	 * 资源id
	 */
	private String resourceId;
}
