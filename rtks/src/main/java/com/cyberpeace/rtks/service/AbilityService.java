package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.req.AbilityListReq;
import com.cyberpeace.rtks.dao.entity.Ability;

/**
 * 能力Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface AbilityService extends IService<Ability> {

	/**
	 * 分页
	 *
	 * @param abilityListReq 能力列表请求体
	 * @return {@code ListRsp<Ability> }
	 */
	ListRsp<Ability> page(AbilityListReq abilityListReq);

	/**
	 * 给定数组的列表，只要其中任意一个id对应的能力不存在 报错
	 *
	 * @param ids id列表
	 */
	void checkIds(List<String> ids);
}
