package com.cyberpeace.situation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingUpdateReq;
import com.cyberpeace.situation.controller.res.DynamicEffectMappingRes;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午4:39
 */
public interface DynamicEffectMappingService extends IService<DynamicEffectsMapping> {

	/**
	 * 新增动效映射
	 *
	 * @param dynamicEffectMappingReq
	 * @return
	 */
	String createDynamicEffectsMapping(DynamicEffectMappingReq dynamicEffectMappingReq);

	/**
	 * 获取动效映射列表
	 *
	 * @param dynamicEffectMappingFilterReq
	 * @return
	 */
	ListRsp<DynamicEffectMappingRes>
			getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq);

	/**
	 * 活动动效映射详情
	 *
	 * @param resourceId
	 * @return
	 */
	DynamicEffectsMapping getDynamicEffectsMapping(String resourceId, Boolean throwNull);

	/**
	 * 批量删除动效映射
	 *
	 * @param batchDeleteReq
	 * @return
	 */
	Integer deleteDynamicEffectsMapping(BatchDeleteReq batchDeleteReq);

	/**
	 * 修改动效映射
	 *
	 * @param dynamicEffectMappingUpdateReq
	 * @return
	 */
	String patchDynamicEffectsMapping(DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq);
}
