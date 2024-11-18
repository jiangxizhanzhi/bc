package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.mapper.CrBadgeMapper;
import com.cyberpeace.cr.crbase.service.CrBadgeService;
import com.cyberpeace.cs.service.context.BadgeBo;

import lombok.extern.slf4j.Slf4j;

/**
 * 靶场徽章Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see CrBadgeService
 * @since 1.0.0
 */
@Service
@Slf4j
public class CrBadgeServiceImpl implements CrBadgeService {

	/**
	 * 靶场徽章映射
	 *
	 * @see CrBadgeMapper
	 */
	@Autowired
	private CrBadgeMapper crBadgeMapper;

	/**
	 * 创造批量
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userIds    用户id列表
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<String>
	 */
	@Override
	public void createBatch(String businessId, Integer type, List<String> userIds) {
		List<CrBadge> crBadgeList = Lists.newArrayList();
		for (String userId : userIds) {
			CrBadge badge = new CrBadge();
			badge.setBusinessId(businessId);
			badge.setUserId(userId);
			badge.setType(type);

			crBadgeList.add(badge);
		}
		crBadgeMapper.insertBatch(crBadgeList);
	}

	/**
	 * 如果存在
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userId     用户id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean ifExist(String businessId, Integer type, String userId) {
		return crBadgeMapper.existByBusinessIdAndTypeAndUserId(businessId, type, userId);
	}

	/**
	 * 删除徽章
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userId     用户id
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 */
	@Override
	public void removeBadge(String businessId, Integer type, String userId) {
		crBadgeMapper.removeBatchByBusinessIdAndTypeAndUserId(businessId, type, userId);
	}

	/**
	 * 获取评估徽章
	 *
	 * @param roomId 房间id
	 * @return {@code BadgeBo }
	 * @throws
	 * @see String
	 * @see BadgeBo
	 */
	@Override
	public BadgeBo getEvaluationBadge(String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		Boolean res = this.ifExist(roomId, CrBadge.BadgeType.NEW_SUBMIT.getCode(), userId);
		BadgeBo bo = new BadgeBo();
		bo.setShow(res);
		return bo;
	}

	/**
	 * 点击评估徽章
	 *
	 * @param roomId 房间id
	 * @throws
	 * @see String
	 */
	@Override
	public void clickEvaluationBadge(String roomId) {
		this.removeBadge(roomId, CrBadge.BadgeType.NEW_SUBMIT.getCode(),
				UserInfoContext.getUserContext().getUserId());
	}
}
