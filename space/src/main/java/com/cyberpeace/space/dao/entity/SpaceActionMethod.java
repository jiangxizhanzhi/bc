package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@TableName("space_action_method")
public class SpaceActionMethod extends SpaceFrameBaseTable {

	private String description;

	private String tag;

	public SpaceActionMethodBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionMethodBo bo = BeanUtils.copy(this, SpaceActionMethodBo.class);
		// bo.setTarget(relatedMap.get(bo.getResourceId()));
		bo.setTag(JsonUtil.toJsonObjectArray(this.tag, String.class));
		return bo;
	}
}