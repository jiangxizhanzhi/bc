package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 根据id批量查询行为接口
 */
@Data
public class LcBehaviorQuery implements Serializable {

	private List<Long> ids = Lists.newArrayList();

	private Boolean withVariable = false;

	public void addIds(Collection<String> ids) {
		ids.forEach(id -> this.ids.add(new Long(id)));
	}
}
