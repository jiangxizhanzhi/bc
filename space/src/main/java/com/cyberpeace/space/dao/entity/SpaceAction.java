package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionBo;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_action")
public class SpaceAction extends SpaceFrameBaseTable {

	private Integer type;

	private String description;

	private String method;

	private String environment;

	private String action;

	@TableField("`analyze`")
	private String analyze;

	private String tool;

	private String strategy;

	public SpaceActionBo entity2Bo(Map<String, IdAndName> parentMap) {
		SpaceActionBo bo = BeanUtils.copy(this, SpaceActionBo.class);
		bo.setMethod(this.getIdAndName(parentMap, this.method));
		return bo;
	}
}