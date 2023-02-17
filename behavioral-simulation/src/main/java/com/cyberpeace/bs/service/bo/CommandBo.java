package com.cyberpeace.bs.service.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 配管下发的命令
 */
@Data
public class CommandBo<T> implements Serializable {

	private String taskId;

	private String subTaskId;

	private String xdschemaId;

	private String agentType;

	private String reportUrl;

	private List<Configuration<T>> configurations;

	private String configTaskId;

	private AgentInfo agentInfo;

	public T getCommandBody() {
		return configurations.get(0).getPolicy();
	}
}

@Data
class Configuration<T> implements Serializable {

	private TargetInfo targetInfo;

	private T policy;

	private String action;
}

@Data
class TargetInfo implements Serializable {

	private String id;

	private String host;

	private String name;

	private String resourceId;

	private String imageType;
}

@Data
class AgentInfo implements Serializable {

	private String agentId;

	private String agentType;

	private String url;
}