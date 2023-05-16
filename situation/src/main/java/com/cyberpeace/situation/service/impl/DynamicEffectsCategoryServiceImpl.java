package com.cyberpeace.situation.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.situation.dao.entity.DynamicEffectsCategory;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsCategoryMapper;
import com.cyberpeace.situation.service.DynamicEffectsCategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DynamicEffectsCategoryServiceImpl extends ServiceImpl<DynamicEffectsCategoryMapper, DynamicEffectsCategory> implements DynamicEffectsCategoryService {

	@Override
	public DynamicEffectsCategory getCategory(String resourceId, Boolean throwNull) {
		DynamicEffectsCategory dynamicEffectsCategory = getById(resourceId);
		if (dynamicEffectsCategory == null && throwNull) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return dynamicEffectsCategory;
	}

}
