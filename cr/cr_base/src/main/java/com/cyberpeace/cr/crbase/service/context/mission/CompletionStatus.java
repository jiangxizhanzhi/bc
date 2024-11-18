package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务完成状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CompletionStatus implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	private Integer status;
}
