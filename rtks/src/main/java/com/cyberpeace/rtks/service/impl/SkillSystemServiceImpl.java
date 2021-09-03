package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.rtks.dao.entity.SkillSystem;
import com.cyberpeace.rtks.dao.mapper.SkillSystemMapper;
import com.cyberpeace.rtks.service.SkillSystemService;
import com.cyberpeace.rtks.service.bo.SceneSkillSystem;

/**
 *
 */
@Service
public class SkillSystemServiceImpl extends ServiceImpl<SkillSystemMapper, SkillSystem> implements SkillSystemService {

	@Autowired
	private SkillSystemMapper skillSystemMapper;

	@Override
	public ListRsp<SkillSystem> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<SkillSystem> result = skillSystemMapper.listSkillSystem(page,
				pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}

	@Override
	public void updateSceneSkillSystem(SceneSkillSystem sceneSkillSystemAddReq) {
		skillSystemMapper.updateDefaultSceneSkillSystem();

		skillSystemMapper.updateSceneSkillSystem(sceneSkillSystemAddReq.getAttackSkillSystemId(),
				SceneSkillSystem.ATTACK_ID);
		skillSystemMapper.updateSceneSkillSystem(sceneSkillSystemAddReq.getDefenseSkillSystemId(),
				SceneSkillSystem.DEFENSE_ID);

	}

}
