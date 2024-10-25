package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodBo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@TableName("space_action_method")
public class SpaceActionMethod extends SpaceFrameBaseTable {

	private String description;

	public SpaceActionMethodBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionMethodBo bo = BeanUtils.copy(this, SpaceActionMethodBo.class);
		bo.setTarget(relatedMap.get(bo.getResourceId()));
		return bo;
	}
}