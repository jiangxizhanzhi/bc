package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * 任务完成状态
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CompletionStatus implements Serializable {

	private String resourceId;

	private Integer status;
}
