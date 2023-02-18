package com.cyberpeace.cr.scene_manage.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.scene_manage.dao.entity.SceneTemplate;
import com.cyberpeace.cr.scene_manage.service.bo.SceneTemplateBo;

public interface SceneTemplateService extends IService<SceneTemplate> {

	/**
	 * 获取场景管理模板数据
	 * @param businessTemplateId 业务模板id 
	 * @return 场景管理训练模板数据
	 */
	SceneTemplateBo get(String businessTemplateId);

	/**
	 * 更新场景管理训练模板
	 * @param businessTemplateId 业务模板id 
	 * @param baseInfo 模板基础信息
	 * @param missionsScheme 任务编排
	 * @return 更新后的场景管理模板
	 */
	SceneTemplateBo update(String businessTemplateId, BaseInfo baseInfo,
						   JSONObject missionsScheme);

	/**
	 * 获取场景管理模板
	 * @param businessTemplateId 业务模板id
	 * @return 场景管理模板
	 */
	SceneTemplate getByBusinessTemplateId(String businessTemplateId);

	/**
	 * 根据 missions_scheme 获取任务模板id列表
	 * @param missionsScheme 任务编排json
	 * @return 任务模板id列表
	 */
	List<String> getMissionTemplateIdsByMissionsScheme(String missionsScheme);
}
