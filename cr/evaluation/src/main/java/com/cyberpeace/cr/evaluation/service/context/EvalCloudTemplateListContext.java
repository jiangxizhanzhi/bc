package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

@Data
public class EvalCloudTemplateListContext {

	private String resourceId;

	private String name;

	private String target;

	private Integer type;

	private String createUserId;

	private String topoId;
}
