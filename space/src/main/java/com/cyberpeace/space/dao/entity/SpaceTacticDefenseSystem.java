package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceTacticDefenseSystemBo;
import lombok.Data;

import java.util.Map;

/**
 * 空间战法防御系统
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Data
@TableName("space_tactic_defense_system")
public class SpaceTacticDefenseSystem extends SpaceBaseTable {

	private Integer stage;

	private String definition;

	public SpaceTacticDefenseSystemBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTacticDefenseSystemBo bo = BeanUtils.copy(this, SpaceTacticDefenseSystemBo.class);
		return bo;
	}
}
