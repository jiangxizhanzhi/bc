package com.cyberpeace.situation.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.situation.dao.entity.DynamicEffect;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsMapper;
import com.cyberpeace.situation.service.DynamicEffectsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DynamicEffectsServiceImpl extends ServiceImpl<DynamicEffectsMapper, DynamicEffect> implements DynamicEffectsService {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private DynamicEffectsMapper dynamicEffectsMapper;

	@Override
	public Boolean clearDynamicEffects(DynamicEffect dynamicEffect, Boolean deleteAttachment) {

		// 1、如果路径已解压，清理解压后的动效路径
		if (dynamicEffect.getDynamicDir() != null) {
			FileUtil.deleteDir(new File(dynamicEffect.getDynamicDir()));
		}

		// 2、如果需要删除附件，则删除相应数据
		if (Boolean.TRUE.equals(deleteAttachment)) {
			// 从路径中提取无后缀的文件名
			String attachmentName = dynamicEffect.getDynamicFile()
					.substring(dynamicEffect.getDynamicFile().lastIndexOf("/") + 1);
			// 删除附件
			attachmentService.deleteAttachment(attachmentName);
		}

		return true;
	}

	@Override
	public DynamicEffect getDynamicEffect(String resourceId, Boolean throwNull) {
		DynamicEffect dynamicEffect = getById(resourceId);
		if (dynamicEffect == null && throwNull) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return dynamicEffect;
	}

	@Override
	public Map<String, String> getDynamicEffectMap() {
		List<DynamicEffect> dynamicEffects = dynamicEffectsMapper
				.selectList(new LambdaQueryWrapper<>());
		return dynamicEffects.stream()
				.collect(Collectors.toMap(DynamicEffect::getResourceId, DynamicEffect::getName));
	}

	@Override
	public Map<String, DynamicEffect> getIdEffectMap() {
		List<DynamicEffect> dynamicEffects = dynamicEffectsMapper
				.selectList(new LambdaQueryWrapper<>());
		return dynamicEffects.stream().collect(
				Collectors.toMap(DynamicEffect::getResourceId, dynamicEffect -> dynamicEffect));
	}
}
