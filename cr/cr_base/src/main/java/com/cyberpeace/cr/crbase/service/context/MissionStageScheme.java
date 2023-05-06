package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

/**
 *
 * 任务阶段编排
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionStageScheme {

	private String stage;

	private List<String> missions;

	private List<String> dynamic;
}
