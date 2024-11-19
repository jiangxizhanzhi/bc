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
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface AbilityService extends IService<Ability> {

	/**
	 * 分页
	 *
	 * @param abilityListReq 能力列表请求体
	 * @return {@code ListRsp<Ability> }
	 * @throws
	 * @see AbilityListReq
	 * @see ListRsp<Ability>
	 */
	ListRsp<Ability> page(AbilityListReq abilityListReq);

	/**
	 * 给定数组的列表，只要其中任意一个id对应的能力不存在 报错
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	void checkIds(List<String> ids);
}
