package com.cyberpeace.cr.singletrain.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;
import com.cyberpeace.cr.singletrain.service.bo.mission.SingleMatrixProgressRsp;

public interface SingleTemplateService extends IService<SingleTemplate> {

	/**
	 * 获取单兵模板数据
	 * @param businessTemplateId 业务模板id 
	 * @return 单兵训练模板数据
	 */
	SingleTemplateBo get(String businessTemplateId);

	/**
	 * 更新单兵训练模板
	 * @param businessTemplateId 业务模板id 
	 * @param baseInfo 模板基础信息
	 * @param missionsScheme 任务编排
	 * @return 更新后的单兵模板
	 */
	SingleTemplateBo update(String businessTemplateId, BaseInfo baseInfo,
			JSONObject missionsScheme);

	/**
	 * 获取单兵模板
	 * @param businessTemplateId 业务模板id
	 * @return 单兵模板
	 */
	SingleTemplate getByBusinessTemplateId(String businessTemplateId);

	/**
	 * 根据 missions_scheme 获取任务模板id列表
	 * @param missionsScheme 任务编排json
	 * @return 任务模板id列表
	 */
	List<String> getMissionTemplateIdsByMissionsScheme(String missionsScheme);

	/**
	 * 获取任务矩阵
	 *
	 * @param btId 模板id
	 * @return {@code SingleMatrixProgressRsp}
	 */
	SingleMatrixProgressRsp getSingleMatrix(String btId);
}
