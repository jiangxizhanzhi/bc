package com.cyberpeace.bs.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserBehavior;
import com.cyberpeace.bs.dao.mapper.UserBehaviorMapper;
import com.cyberpeace.bs.service.BsUtil;
import com.cyberpeace.bs.service.UserBehaviorService;
import com.cyberpeace.bs.service.bo.behavior.*;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBehaviorServiceImpl implements UserBehaviorService {

	@Autowired
	private UserBehaviorMapper userBehaviorMapper;

	@Override
	public ListRsp<UserBehaviorBo> listUserBehavior(Page page, Integer userType, String pattern) {
		// get user_behaviors
		IPage<UserBehavior> userBehaviorIPage = userBehaviorMapper.pageUserBehaviors(page, userType,
				pattern);
		userBehaviorIPage.convert((userBehavior -> {
			UserBehaviorBo userBehaviorBo = BeanUtils.copy(userBehavior, UserBehaviorBo.class);
			BsUtil.setBehaviorStatistic(userBehaviorBo, userBehavior.getBehaviorList());
			return userBehaviorBo;
		}));
		return PageUtil.getListResult(userBehaviorIPage);
	}

	@Override
	public UserBehaviorDetail getDetail(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			return null;
		}
		UserBehavior userBehavior = userBehaviorMapper.selectById(resourceId);
		if (userBehavior == null) {
			return null;
		}
		UserBehaviorDetail detail = BeanUtils.copy(userBehavior, UserBehaviorDetail.class);
		// get behaviors
		List<BehaviorSimple> behaviorSimpleList = JsonUtil
				.toJsonObjectArray(userBehavior.getBehaviorList(), BehaviorSimple.class);
		List<String> behaviorIds = behaviorSimpleList.stream().map(BehaviorSimple::getId)
				.collect(Collectors.toList());
		List<Behavior> behaviors = Lists.newArrayList();
		detail.setBehaviors(behaviors);
		// todo get behaviors from lc
		return detail;
	}

	@Override
	public void create(UserBehaviorAddBo addBo) {
		UserBehavior userBehavior = req2Entity(addBo);
		userBehaviorMapper.insert(userBehavior);
	}

	@Override
	public void update(UserBehaviorModifyBo modifyBo) {
		if (StringUtils.isBlank(modifyBo.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserBehavior userBehavior = req2Entity(modifyBo);
		userBehaviorMapper.updateById(userBehavior);
	}

	@Override
	public void delete(List<String> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return;
		}
		userBehaviorMapper.deleteBatchIds(resourceIds);
	}

	private <T extends UserBehaviorAddBo> UserBehavior req2Entity(T reqBo) {
		UserBehavior userBehavior = BeanUtils.copy(reqBo, UserBehavior.class);
		List<BehaviorSimple> behaviorSimpleList = BeanUtils.deepCopyList(reqBo.getBehaviors(),
				BehaviorSimple.class);
		// 保证顺序
		BsUtil.sortBehaviorList(behaviorSimpleList);
		userBehavior.setBehaviorList(
				JsonUtil.arrayToString(behaviorSimpleList, PropertyNamingStrategy.SnakeCase));
		return userBehavior;
	}
}
