package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 场景节点列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/30
 * @see TopoNodeListReq
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Slf4j
public class SceneNodeListReq extends TopoNodeListReq implements Serializable {

	/**
	 * 实时主机状态
	 *
	 * @see Boolean
	 */
	private Boolean realTimeHostState;

	/**
	 * 筛选节点id
	 *
	 * @see List<String>
	 */
	private List<String> elementIds;

	/**
	 * 系统类型名称
	 *
	 * @see String
	 */
	private String systemTypeName;

	@Override
	protected List<String> getQuery() {
		List<String> queries = super.getQuery();
		if (Boolean.TRUE.equals(this.realTimeHostState)) {
			queries.add("realTimeHostState=true");
		} else {
			queries.add("realTimeHostState=false");
		}
		if (StringUtils.isNotBlank(this.systemTypeName)) {
			queries.add("systemTypeName=" + this.systemTypeName);
		}
		if (CollectionUtils.isNotEmpty(elementIds)) {
			queries.add("elementIds=" + String.join(",", this.elementIds));
		}
		return queries;
	}
}
