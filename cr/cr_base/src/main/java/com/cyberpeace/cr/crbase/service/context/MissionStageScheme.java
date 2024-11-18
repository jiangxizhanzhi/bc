package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

/**
 * 任务阶段编排
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionStageScheme {

	/**
	 * 阶段
	 *
	 * @see String
	 */
	private String stage;

	/**
	 * 任务
	 *
	 * @see List<String>
	 */
	private List<String> missions;

	/**
	 * 动态
	 *
	 * @see List<String>
	 */
	private List<String> dynamic;
}
