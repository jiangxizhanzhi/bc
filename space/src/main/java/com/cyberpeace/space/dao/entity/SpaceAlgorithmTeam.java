package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmTeamBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@TableName("space_algorithm_team")
public class SpaceAlgorithmTeam extends SpaceBaseTable {

	/**
	 * 专业知识百分比
	 */
	private Integer expertisePercent;

	/**
	 * 岗位百分比
	 */
	private Integer rolePercent;

	public SpaceAlgorithmTeamBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmTeamBo bo = BeanUtils.copy(this, SpaceAlgorithmTeamBo.class);
		return bo;
    }
}
    