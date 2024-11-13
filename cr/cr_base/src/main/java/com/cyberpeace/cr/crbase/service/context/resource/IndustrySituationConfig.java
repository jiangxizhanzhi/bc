package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 行业态势yaml格式
 *
 * @author SN
 * @date 2023/09/13
 */
@Data
public class IndustrySituationConfig implements Serializable {

	/**
	 * 任务
	 */
	private List<IndustrySituationTask> task;

	/**
	 * Stage Path （阶段路径）
	 */
	private List<String> stage_path;
}
