package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionAttackBo;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import com.cyberpeace.space.service.context.frame.SpaceActionTool;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_action_attack")
public class SpaceActionAttack extends SpaceFrameBaseTable {

	private String description;

	private String method;

	private String environment;

	private String action;

	@TableField("`analyze`")
	private String analyze;

	private String tool;

	private String strategy;

	public SpaceActionAttackBo entity2Bo(Map<String, IdAndName> parentMap) {
		SpaceActionAttackBo bo = BeanUtils.copy(this, SpaceActionAttackBo.class);
		bo.setMethod(this.getIdAndName(parentMap, this.method));
		bo.setTool(JsonUtil.toJsonObjectArray(this.tool, SpaceActionTool.class));
		bo.setStrategy(JsonUtil.toJsonObjectArray(this.strategy, SpaceActionStrategy.class));
		return bo;
	}
}