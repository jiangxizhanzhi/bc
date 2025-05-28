package com.cyberpeace.space.model;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间Bo基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class SpaceBoBase {

	private String resourceId;
	private Boolean builtin;
	public abstract SpaceBaseTable bo2Entity();

	public String getIds(List<IdAndName> list){
		List<String> ids;
		if(null == list){
			ids = Lists.newArrayList();
		} else {
			ids = StreamApiUtils.map(list, IdAndName::getResourceId);
		}
		return JsonUtil.arrayToString(ids);
	}
}
