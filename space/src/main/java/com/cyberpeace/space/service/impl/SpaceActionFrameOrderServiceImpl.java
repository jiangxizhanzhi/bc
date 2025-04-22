
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameOrderMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameOrderService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderListReq;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderReq;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceActionFrameOrderServiceImpl extends ServiceImpl<SpaceActionFrameOrderMapper, SpaceActionFrameOrder> implements SpaceActionFrameOrderService {

	@Autowired
	private SpaceActionFrameOrderMapper mapper;

	@Autowired
	private SpaceTacticAttackMapper attackMapper;

	@Autowired
	private SpaceTacticDefenseMapper defenseMapper;

	@Autowired
	private SpaceActionFrameApplicationService applicationService;

	@Override
	public void create(SpaceActionFrameOrderReq req) {
		SpaceActionFrameOrder entity = BeanUtils.copy(req, SpaceActionFrameOrder.class);
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);
		UserInfo userInfo = UserInfoContext.getUserContext();
		entity.setSender(userInfo.getUserId());
		entity.setSenderName(userInfo.getName());
		this.save(entity);
	}

	@Override
	public ListRsp<SpaceActionFrameOrderBo> pageMyOrders(SpaceActionFrameOrderListReq req) {
		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper =  req.req2QueryWrapper();
		if (StringUtils.isNotBlank(req.getUser_id())) {
			wrapper.eq(SpaceActionFrameOrder::getUserId, req.getUser_id());
		} else {
			wrapper.eq(SpaceActionFrameOrder::getUserId,
					UserInfoContext.getUserContext().getUserName());
		}
		Map<String, String> tacticNameMap = Maps.newHashMap();
		if(StringUtils.isNotBlank(req.getPattern())){
			LambdaQueryWrapper<SpaceTacticAttack> attackLambdaQueryWrapper = new LambdaQueryWrapper<>();
			attackLambdaQueryWrapper.like(SpaceTacticAttack::getName, req.getPattern());
			List<SpaceTacticAttack> attackList = attackMapper.selectList(attackLambdaQueryWrapper);
			for (SpaceTacticAttack attack : attackList) {
				tacticNameMap.put(attack.getResourceId(), attack.getName());
			}
			LambdaQueryWrapper<SpaceTacticDefense> defenseLambdaQueryWrapper = new LambdaQueryWrapper<>();
			defenseLambdaQueryWrapper.like(SpaceTacticDefense::getName, req.getPattern());
			List<SpaceTacticDefense> defenseList = defenseMapper.selectList(defenseLambdaQueryWrapper);
			for (SpaceTacticDefense defense : defenseList) {
				tacticNameMap.put(defense.getResourceId(),defense.getName());
			}
			if (tacticNameMap.isEmpty()) {
				return PageUtil.getListResult(page);
			} else {
				wrapper.in(SpaceActionFrameOrder::getTacticId, tacticNameMap.keySet());
			}
		}
		Map<String, String> userMap = applicationService.getUserMap(req.getRvb_id(), SpaceCommonConstant.RvbUserGroup.MIDDLE);
		IPage<SpaceActionFrameOrder> iPage = mapper.selectPage(page, wrapper);
		IPage<SpaceActionFrameOrderBo> resPage = iPage.convert(item -> {
			SpaceActionFrameOrderBo bo = BeanUtils.copy(item, SpaceActionFrameOrderBo.class);
			bo.setReleased(true);
			bo.setTacticName(tacticNameMap.get(item.getTacticId()));
			bo.setSenderName(userMap.get(item.getSender()));
			return bo;
		});

		return PageUtil.getListResult(resPage);
	}

	@Override
	public void complete(String resourceId) {
		if(StringUtils.isBlank(resourceId)){
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		SpaceActionFrameOrder entity = mapper.selectById(resourceId);
		entity.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
		this.updateById(entity);
	}

}

    