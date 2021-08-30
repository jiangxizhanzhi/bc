package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.req.AbilityListReq;
import com.cyberpeace.rtks.dao.entity.Ability;

public interface AbilityService extends IService<Ability> {

	ListRsp<Ability> page(AbilityListReq abilityListReq);

	/**
	 * 给定数组的列表，只要其中任意一个id对应的能力不存在 报错
	 * */
	void checkIds(List<String> ids);
}
