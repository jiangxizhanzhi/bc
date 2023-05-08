package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * 上下文元素流列表
 * 节点流量查询体
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ElementFlowListContext implements Serializable {

	private String mpSceneId;

	private String topoId;

	private String nodeId;

	private Integer interactiveType;

	private Page page;

}
