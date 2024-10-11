package com.cyberpeace.integratation.model.scene;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 场景节点列表请求体
 *
 * @author SN
 */
@Data
@Slf4j
public class SceneNodeListReq extends TopoNodeListReq implements Serializable {

	/**
	 * 实时主机状态
	 */
	private Boolean realTimeHostState;

	/**
	 * 筛选节点id
	 */
	private List<String> elementIds;

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
