package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务阶段编排
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionStageScheme {

	/**
	 * 阶段
	 */
	private String stage;

	/**
	 * 任务
	 */
	private List<String> missions;

	/**
	 * 动态
	 */
	private List<String> dynamic;
}
