package com.cyberpeace.situation.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.ElementSlimContext;
import com.cyberpeace.integratation.model.situation.TopoContext;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
@TableName("situation_topo_info")
public class TopoConfig extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String subRangeId;

	private String taskId;

	private String subjectId;

	private String envId;

	private String config;

	public TopoConfig() {}

	public TopoConfig(String subRangeId, String taskId, String subjectId, String envId,
			TopoContext topoContext) {

		this.subRangeId = subRangeId;
		this.taskId = taskId;
		this.subjectId = subjectId;
		this.envId = envId;

		Map<String, ElementSlimContext> elementSlimContextMap = Maps.newHashMap();
		topoContext.getElements().forEach((id, element) -> {
			elementSlimContextMap.put(id, new ElementSlimContext(element));
		});

		this.config = JsonUtil.toJsonString(elementSlimContextMap);
	}

}
