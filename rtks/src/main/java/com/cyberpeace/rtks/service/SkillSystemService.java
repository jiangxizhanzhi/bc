package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.SkillSystem;
import com.cyberpeace.rtks.service.bo.SceneSkillSystem;

/**
 *
 */
public interface SkillSystemService extends IService<SkillSystem> {

	ListRsp<SkillSystem> page(PageReqBase pageReqBase);

	/**修改场景类型和技能体系对应关系
	 * @param sceneSkillSystemAddReq
	 */
	void updateSceneSkillSystem(SceneSkillSystem sceneSkillSystemAddReq);

}
