package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.service.bo.xiamen2c.EvaluationSkillBo;
import com.cyberpeace.rtks.service.bo.xiamen2c.EvaluationSkillDetail;
import com.cyberpeace.rtks.service.bo.xiamen2c.EvaluationTraineeBo;
import com.cyberpeace.rtks.service.bo.xiamen2c.RoleSimple;

public interface SingleEvaluationService {

	/**
	 * 获取技能列表
	 * @param page 分页信息
	 * @param roleId 岗位id
	 * @return 技能分页列表
	 */
	ListRsp<EvaluationSkillBo> listSkill(Page page, String roleId);

	/**
	 * 获取技能详情
	 * @param skillId 技能id
	 * @return 技能详情
	 */
	EvaluationSkillDetail getSkillDetail(String skillId);

	/**
	 * 获取参训人员信息分页列表
	 * @param page 分页信息
	 * @param roleId 岗位id
	 * @return 参训人员信息分页列表
	 */
	ListRsp<EvaluationTraineeBo> listTrainee(Page page, String roleId);

	/**
	 * 岗位列表下拉菜单
	 * @return 岗位列表
	 */
	List<RoleSimple> listRoles();
}
