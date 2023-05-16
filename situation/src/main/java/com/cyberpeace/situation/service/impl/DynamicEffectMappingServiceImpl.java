package com.cyberpeace.situation.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.topo.Color;
import com.cyberpeace.situation.constant.topo.DynamicType;
import com.cyberpeace.situation.constant.topo.LineType;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingUpdateReq;
import com.cyberpeace.situation.controller.res.DynamicEffectMappingRes;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.mapper.DynamicEffectMappingMapper;
import com.cyberpeace.situation.service.DynamicEffectMappingService;
import com.cyberpeace.situation.service.DynamicEffectsService;
import com.cyberpeace.situation.service.ThirdPartyService;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午4:40
 */
@Service
public class DynamicEffectMappingServiceImpl extends ServiceImpl<DynamicEffectMappingMapper, DynamicEffectsMapping> implements DynamicEffectMappingService {

	@Autowired
	private DynamicEffectMappingMapper dynamicEffectMappingMapper;

	@Autowired
	private DynamicEffectsService dynamicEffectsService;

	@Autowired
	private ThirdPartyService thirdPartyService;

	private void checkEffectsMapExist(String attackType, Integer type, String name,
			String resourceId) {

		// 如果同类型动效映射已存在，则抛出异常
		if (dynamicEffectMappingMapper.checkEffectsMapExist(attackType, type, resourceId)) {
			throw new BusinessException(SituationErrorCode.DYNAMIC_EFFECT_MAPPING_EXIST);
		}

		// 如果同名动效映射已存在，则抛出异常
		if (dynamicEffectMappingMapper.checkMapNameExist(name, resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

	@Override
	public String createDynamicEffectsMapping(DynamicEffectMappingReq dynamicEffectMappingReq) {

		// 1、转换参数
		DynamicEffectsMapping dynamicEffectsMapping = convertDynamicEffectsMapping(
				dynamicEffectMappingReq, IDGeneratorUtil.getID());

		// 2、写入数据库
		dynamicEffectMappingMapper.insert(dynamicEffectsMapping);

		return dynamicEffectsMapping.getResourceId();
	}

	private DynamicEffectsMapping convertDynamicEffectsMapping(
			DynamicEffectMappingReq dynamicEffectMappingReq, String resourceId) {

		// 1、提取基础参数
		DynamicEffectsMapping dynamicEffectsMapping = BeanUtils.copy(dynamicEffectMappingReq,
				DynamicEffectsMapping.class);

		// 2、写入用户ID
		// String userId = UserInfoContext.getUserContext().getUserId();
		// dynamicEffectsMapping.setCreateUserId(userId);

		// 3、检查同类型动效映射是否存在
		checkEffectsMapExist(dynamicEffectMappingReq.getAttackType(),
				dynamicEffectsMapping.getType(), dynamicEffectMappingReq.getName(), resourceId);

		// 4、设置资源ID
		dynamicEffectsMapping.setResourceId(resourceId);

		return dynamicEffectsMapping;

	}

	@Override
	public ListRsp<DynamicEffectMappingRes>
			getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq) {

		// 1、获取分页查询结果
		Page page = dynamicEffectMappingFilterReq.toPageParam();
		ListRsp<DynamicEffectMappingRes> dynamicEffectsMappingListRsp = PageUtil.getListResult(
				this.getBaseMapper().selectPage(page, dynamicEffectMappingFilterReq));

		List<DynamicEffectMappingRes> list = dynamicEffectsMappingListRsp.getList();

		// 2、如果查询结果为空，直接返回
		if (CollectionUtils.isEmpty(list)) {
			return dynamicEffectsMappingListRsp;
		}

		Map<String, String> eventTypeMap = thirdPartyService.getEventTypesMap();
		Map<String, String> dynamicEffectMap = dynamicEffectsService.getDynamicEffectMap();

		// 3、处理回显参数
		for (DynamicEffectMappingRes mapping : list) {

			// 设置类型翻译
			mapping.setCategory(DynamicType.getDescByCode(mapping.getType()));

			// 设置颜色翻译
			mapping.setColor(Color.getColorByCode(mapping.getColor()).getDesc());

			// 设置线型翻译
			mapping.setLine(LineType.getLineTypeByCode(mapping.getLine()).getDesc());

			// 设置攻击类型翻译
			mapping.setAttackType(eventTypeMap.get(mapping.getAttackType()));

			// 设置动效名称
			mapping.setSourceNodeDynamic(dynamicEffectMap.get(mapping.getSourceNodeDynamic()));
			mapping.setTargetNodeDynamic(dynamicEffectMap.get(mapping.getTargetNodeDynamic()));
		}

		// 4、更新数据
		dynamicEffectsMappingListRsp.setList(list);

		return dynamicEffectsMappingListRsp;
	}

	@Override
	public DynamicEffectsMapping getDynamicEffectsMapping(String resourceId, Boolean throwNull) {
		DynamicEffectsMapping dynamicEffectsMapping = dynamicEffectMappingMapper
				.selectById(resourceId);
		if (null == dynamicEffectsMapping && throwNull) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return dynamicEffectsMapping;
	}

	@Override
	public Integer deleteDynamicEffectsMapping(BatchDeleteReq batchDeleteReq) {

		// 1、检查资源是否存在
		List<String> resourceIds = batchDeleteReq.getIds();
		List<DynamicEffectsMapping> dynamicEffectsMappings = dynamicEffectMappingMapper
				.selectBatchIds(resourceIds);
		if (dynamicEffectsMappings.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 2、检查是否有内置映射
		for (DynamicEffectsMapping mapping : dynamicEffectsMappings) {
			if (DynamicType.PRESET.getCode().equals(mapping.getType())) {
				throw new BusinessException(SituationErrorCode.PRESET_MAPPING_CAN_NOT_DELETE);
			}
		}

		// 3、删除数据
		dynamicEffectMappingMapper.deleteBatchIds(resourceIds);

		return resourceIds.size();
	}

	@Override
	public String patchDynamicEffectsMapping(
			DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq) {

		// 1、校验映射数据是否存在
		String resourceId = dynamicEffectMappingUpdateReq.getResourceId();
		DynamicEffectsMapping mapping = getDynamicEffectsMapping(resourceId, true);

		// 2、如果映射为内置，不允许修改
		if (DynamicType.PRESET.getCode().equals(mapping.getType())) {
			throw new BusinessException(SituationErrorCode.PRESET_MAPPING_CAN_NOT_UPDATE);
		}

		// 3、更新映射数据
		DynamicEffectsMapping dynamicEffectsMapping = convertDynamicEffectsMapping(
				dynamicEffectMappingUpdateReq, dynamicEffectMappingUpdateReq.getResourceId());
		dynamicEffectMappingMapper.updateById(dynamicEffectsMapping);
		return dynamicEffectsMapping.getResourceId();
	}
}
