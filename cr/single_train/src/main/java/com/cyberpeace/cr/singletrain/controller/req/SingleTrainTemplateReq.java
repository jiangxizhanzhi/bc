package com.cyberpeace.cr.singletrain.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import lombok.Data;

/**
 * 单训练模板请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BasicTemplate
 * @since 1.0.0
 */
@Data
public class SingleTrainTemplateReq extends BasicTemplate {

	/**
	 * 任务计划
	 *
	 * @see CrRoleConfigMissionsScheme
	 */
	private CrRoleConfigMissionsScheme missionsScheme;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

}
