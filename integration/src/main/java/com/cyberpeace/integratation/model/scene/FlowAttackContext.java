package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FlowAttackContext {

	/**
	 * 场景id,多个id用逗号分隔
	 */
	private String sceneId;

	/**
	 * 节点id
	 */
	private List<String>  elementIds;

	private Date startTime;

	private Date endTime;
}
