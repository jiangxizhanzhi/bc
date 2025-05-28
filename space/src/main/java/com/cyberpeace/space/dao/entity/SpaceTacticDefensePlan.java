package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.tactic.SpaceTacticDefensePlanBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * 空间战法防御计划
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_tactic_defense_plan")
public class SpaceTacticDefensePlan extends SpaceBaseTable {


	/**
	 * 描述
	 */
	private String description;

	private String plan;

	public SpaceTacticDefensePlanBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTacticDefensePlanBo bo = BeanUtils.copy(this, SpaceTacticDefensePlanBo.class);
		return bo;
    }
}
    