package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

/**
 * 空间警告字典分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceWarningDictionaryPageReq extends SpaceToolPageReq {

	@Override
	public LambdaQueryWrapper<SpaceWarningDictionary> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceWarningDictionary> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceWarningDictionary::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceWarningDictionary::getName, this.getPattern());
		}
		if (StringUtils.isNotBlank(this.getTool())) {
			wrapper.like(SpaceWarningDictionary::getToolType, this.getTool());
		}
		wrapper.setEntityClass(SpaceWarningDictionary.class);
		return wrapper;
	}

}
