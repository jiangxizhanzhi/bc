package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class ElementFlowListContext implements Serializable {

	private String mpSceneId;

	private String topoId;

	private String nodeId;

	private Integer interactiveType;

	private Page page;

}
