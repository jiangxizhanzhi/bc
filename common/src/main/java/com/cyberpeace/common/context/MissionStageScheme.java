package com.cyberpeace.common.context;

import java.util.List;

import lombok.Data;

/**
 * 任务阶段编排
 */
@Data
public class MissionStageScheme {

	private String stage;

	private List<String> missions;

	private List<String> dynamic;
}
