package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 空间作目标
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_action_target")
public class SpaceActionTarget extends SpaceFrameBaseTable {

	private String description;

	public SpaceActionTargetBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionTargetBo bo = BeanUtils.copy(this, SpaceActionTargetBo.class);
		bo.setFrame(relatedMap.get(bo.getResourceId()));
		return bo;
	}
}