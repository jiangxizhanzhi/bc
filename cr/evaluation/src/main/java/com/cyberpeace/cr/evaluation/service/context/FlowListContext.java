package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

import lombok.Data;

@Data
public class FlowListContext implements Serializable {

	private EvalTask evalTask;

	private String nodeId;

	private Integer interactiveType;

	private Page page;

	public FlowListContext(EvalTask evalTask, String nodeId, Integer interactiveType, Page page) {
		this.evalTask = evalTask;
		this.nodeId = nodeId;
		this.interactiveType = interactiveType;
		this.page = page;
	}

	public FlowListContext() {}
}
