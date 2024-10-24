package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_action_frame")
public class SpaceActionFrame extends SpaceFrameBaseTable {

	private Integer type;

	private Integer role;

	private String attack;

	private String defense;

	private String tag;

	private String topo;

	private String description;

	public SpaceActionFrameBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameBo bo = BeanUtils.copy(this, SpaceActionFrameBo.class);
		bo.setAttack(JsonUtil.toJsonObjectArray(this.attack, String.class));
		bo.setDefense(JsonUtil.toJsonObjectArray(this.defense, String.class));
		bo.setTag(JsonUtil.toJsonObjectArray(this.tag, String.class));
		return bo;
	}
}