package com.cyberpeace.space.model;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public abstract class SpaceBoBase {

	private String resourceId;

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
