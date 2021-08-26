package com.cyberpeace.oj.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.AbilityListReq;
import com.cyberpeace.oj.rtks.dao.entity.Ability;

public interface AbilityService extends IService<Ability> {

	ListRsp<Ability> page(AbilityListReq abilityListReq);

	List<String> filterIds(List<String> ids);
}
