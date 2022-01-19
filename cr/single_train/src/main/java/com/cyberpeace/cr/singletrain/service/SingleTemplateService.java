package com.cyberpeace.cr.singletrain.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;

public interface SingleTemplateService extends IService<SingleTemplate> {

	SingleTemplateBo get(String businessTemplateId);

	SingleTemplateBo update(String businessTemplateId, BaseInfo baseInfo,
			JSONObject missionsScheme);

	SingleTemplate getByBusinessTemplateId(String resourceId);

	/**
	 * 根据 missions_scheme 获取任务模板id列表
	 * @param missionsScheme
	 * @return
	 */
	List<String> getMissionTemplateIdsByMissionsScheme(String missionsScheme);
}
