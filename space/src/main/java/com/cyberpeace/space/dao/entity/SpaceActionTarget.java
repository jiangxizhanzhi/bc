package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_action_target")
public class SpaceActionTarget extends SpaceFrameBaseTable {

	private String description;

	private String frame;

	public SpaceActionTargetBo entity2Bo(Map<String, IdAndName> parentMap) {
		SpaceActionTargetBo bo = BeanUtils.copy(this, SpaceActionTargetBo.class);
		bo.setFrame(this.getIdAndNameList(parentMap, this.frame));
		return bo;
	}
}