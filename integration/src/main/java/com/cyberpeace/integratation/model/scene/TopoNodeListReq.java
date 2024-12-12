package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 拓扑节点列表请求体
 *
 * @author SN
 * @date 2023/05/30
 */
@Getter
@Setter
@Slf4j
public class TopoNodeListReq implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 类别
	 */
	private Integer category;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 是否展示简易型
	 */
	private Boolean isAll;

	protected List<String> getQuery() {
		List<String> queries = Lists.newArrayList();
		if (StringUtils.isNotBlank(name)) {
			queries.add("name=" + this.name);
		}
		if (null != category) {
			queries.add("category=" + this.category);
		}
		// 不传isAll默认不展示简易型
		if (Boolean.TRUE.equals(this.isAll)) {
			queries.add("isAll=true");
		} else {
			queries.add("isAll=false");
		}
		return queries;
	}

	public String toUrl() {
		if (StringUtils.isBlank(this.id)) {
			log.error("blank id for scene or topo");
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		List<String> queries = getQuery();
		if (queries.isEmpty()) {
			return id;
		} else {
			return id + "?" + String.join("&", queries);
		}
	}
}
