package com.cyberpeace.situation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.situation.dao.entity.DynamicEffectsCategory;

public interface DynamicEffectsCategoryService extends IService<DynamicEffectsCategory> {

	DynamicEffectsCategory getCategory(String resourceId, Boolean throwNull);

}
