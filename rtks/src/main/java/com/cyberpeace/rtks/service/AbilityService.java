package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.req.AbilityListReq;
import com.cyberpeace.rtks.dao.entity.Ability;

public interface AbilityService extends IService<Ability> {

	ListRsp<Ability> page(AbilityListReq abilityListReq);

	List<String> filterIds(List<String> ids);
}
