package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.dao.entity.SpaceStrategyFrame;
import com.cyberpeace.space.dao.mapper.SpaceStrategyFrameMapper;
import com.cyberpeace.space.dao.mapper.SpaceStrategyMapper;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.strategy.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceStrategyServiceImpl extends ServiceImpl<SpaceStrategyMapper, SpaceStrategy> implements SpaceStrategyService {

	@Autowired
	private SpaceStrategyFrameMapper frameMapper;

	@Autowired
	private SpaceStrategyMapper mapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public SpaceStrategyFrameBo getFrame() {
		List<SpaceStrategyFrame> strategyList = frameMapper.getAll();
		Map<String, SpaceStrategyFrameBo> map = Maps.newHashMap();
		map.put(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME, new SpaceStrategyFrameBo());
		for (SpaceStrategyFrame strategyFrame : strategyList) {
			SpaceStrategyFrameBo bo = this.entity2Bo(strategyFrame);
			map.put(bo.getResourceId(), bo);
			if (map.containsKey(bo.getParent())) {
				map.get(bo.getParent()).getChildren().add(bo);
			}
		}
		return map.get(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME);
	}

	@Override
	public void addFrame(SpaceStrategyFrameAddReq req) {
		SpaceStrategyFrame frame = this.req2Entity(req);
		// todo 重名校验
		frameMapper.insert(frame);
	}

	@Override
	public void modify(SpaceStrategyFrameModifyReq req) {
		SpaceStrategyFrame frame = frameMapper.selectById(req.getResourceId()) ;
		frame.setName(req.getName());
		frameMapper.updateById(frame);
	}

	@Override
	public void deleteFrame(String resourceId) {
		SpaceStrategyFrame frame = frameMapper.selectById(resourceId);
		if (null == frame) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if (frame.getBuiltin()) {
			throw new BusinessException(SpaceError.BUILTIN_FRAME);
		}
		frameMapper.deleteById(resourceId);
	}

	@Override
	public List<SpaceStrategySimple> listStrategiesByFrame(String frameId, String name) {
		List<SpaceStrategySimple> simpleList = mapper.selectIdAndNameByFrame(frameId, name);
		return simpleList;
	}

	@Override
	public void addStrategy(SpaceStrategyAddReq req) {
		SpaceStrategy strategy = this.req2Entity(req);
		strategy.setView(0);
		strategy.setUserId(UserInfoContext.getUserContext().getUserId());
		mapper.insert(strategy);
	}

	@Override
	public void updateStrategy(SpaceStrategyModifyReq req) {
		SpaceStrategy strategy = this.req2Entity(req);
		strategy.setResourceId(req.getResourceId());
		mapper.updateById(strategy);
	}

	@Override
	public void deleteStrategy(String resourceId) {
		mapper.deleteById(resourceId);
	}

	@Override
	public SpaceStrategyBo getDetail(String resourceId) {
		SpaceStrategy strategy = this.getOrException(resourceId);
		strategy.setView(strategy.getView() + 1);
		mapper.updateById(strategy);
		SpaceStrategyBo bo = BeanUtils.copy(strategy, SpaceStrategyBo.class);
		UserInfo userInfo = remoteUserService.getById(bo.getUserId());
		bo.setUser(userInfo.getName());
		bo.setUserAvatar(userInfo.getAvatarUrl());
		return bo;
	}

	@Override
	public String getFrameRouteById(String frameId) {
		List<String> names = Lists.newArrayList();
		SpaceStrategyFrame frame = this.getFrameOrException(frameId);
		while (!SpaceCommonConstant.DEFAULT_STRATEGY_FRAME.equals(frame.getParent())){
			frame = this.getFrameOrException(frame.getParent());
			names.add(frame.getName());
		}
		Collections.reverse(names);
		return String.join("/", names);
	}

	private SpaceStrategy req2Entity(SpaceStrategyAddReq req){
		SpaceStrategy strategy = BeanUtils.copy(req, SpaceStrategy.class);
		return strategy;
	}

	private SpaceStrategyFrame req2Entity(SpaceStrategyFrameAddReq req) {
		SpaceStrategyFrame frame = new SpaceStrategyFrame();
		frame.setParent(req.getParent());
		frame.setName(req.getName());
		frame.setBuiltin(false);
		return frame;
	}

	private SpaceStrategyFrameBo entity2Bo(SpaceStrategyFrame spaceStrategyFrame) {
		SpaceStrategyFrameBo bo = new SpaceStrategyFrameBo();
		bo.setName(spaceStrategyFrame.getName());
		bo.setResourceId(spaceStrategyFrame.getResourceId());
		bo.setBuiltin(spaceStrategyFrame.getBuiltin());
		bo.setParent(spaceStrategyFrame.getParent());
		return bo;
	}

	private SpaceStrategy getOrException(String resourceId){
		SpaceStrategy strategy = mapper.selectById(resourceId);
		if(null == strategy){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return strategy;
	}

	private SpaceStrategyFrame getFrameOrException(String resourceId){
		SpaceStrategyFrame frame = frameMapper.selectById(resourceId);
		if(null == frame){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return frame;
	}

}
