package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务完成状态
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CompletionStatus implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 状态
	 */
	private Integer status;
}
