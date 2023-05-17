package com.cyberpeace.situation.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.situation.dao.entity.DynamicEffect;

public interface DynamicEffectsService extends IService<DynamicEffect> {

	Boolean clearDynamicEffects(DynamicEffect dynamicEffect, Boolean deleteAttachment);

	DynamicEffect getDynamicEffect(String resourceId, Boolean throwNull);

	Map<String, String> getDynamicEffectMap();

	Map<String, DynamicEffect> getIdEffectMap();

}
