package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 模版任务id
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TemplateMissionId implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;


}
