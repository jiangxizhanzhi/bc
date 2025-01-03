package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@TableName("space_action_frame")
public class SpaceActionFrame extends SpaceFrameBaseTable {

	private Integer type;

	private Integer role;

	private String attack;

	private String defense;

	private String tag;

	private String topo;

	private String description;

	private String frame;

	public SpaceActionFrameBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionFrameBo bo = BeanUtils.copy(this, SpaceActionFrameBo.class);
		bo.setTag(JsonUtil.toJsonObjectArray(this.tag, String.class));
		return bo;
	}

	public enum Type implements GenericEnum {

		PROCESS(0, "面向流程"),TARGET(1, "面向目标");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}