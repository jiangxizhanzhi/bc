package com.cyberpeace.bs.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.mapper.UserModelMapper;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserModelService;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.behavior.BehaviorSimple;
import com.cyberpeace.bs.service.bo.behavior.BehaviorWithVariable;
import com.cyberpeace.bs.service.bo.lc.LcBehaviorRspBo;
import com.cyberpeace.bs.service.bo.lc.LcVariableRspBo;
import com.cyberpeace.bs.service.bo.model.*;
import com.cyberpeace.bs.service.bo.variable.VariableSimple;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.bs.utils.ChartUtil;
import com.cyberpeace.bs.utils.RandomUtil;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserModelServiceImpl implements UserModelService {

	@Autowired
	private UserModelMapper userModelMapper;

	@Autowired
	private LcService lcService;

	@Autowired
	private UserTypeService userTypeService;

	@Override
	public ListRsp<UserModelBo> listUserModel(Page page, String userTypeId, String pattern) {
		// get user_behaviors
		IPage<UserModel> userBehaviorIPage = userModelMapper.pageUserBehaviors(page, userTypeId,
				pattern);
		// get user types
		Set<String> userTypeIds = userBehaviorIPage.getRecords().stream()
				.map(UserModel::getUserTypeId).collect(Collectors.toSet());
		Map<String, String> userTypeMap = userTypeService.getIdNameMap(userTypeIds);
		userBehaviorIPage.convert((userModel -> {
			UserModelBo userBehaviorBo = BeanUtils.copy(userModel, UserModelBo.class);
			BsUtil.setBehaviorStatistic(userBehaviorBo, userModel.getBehaviorList());
			BsUtil.setHabits(userBehaviorBo, userModel.getAccessHabit());
			userBehaviorBo.setUserType(userTypeMap.get(userModel.getUserTypeId()));
			return userBehaviorBo;
		}));
		return PageUtil.getListResult(userBehaviorIPage);
	}

	@Override
	public UserModelDetail getDetail(String resourceId) {
		UserModel userModel = getOrException(resourceId);
		UserModelDetail detail = BeanUtils.copy(userModel, UserModelDetail.class);
		detail.setUserType(userTypeService.getNameById(detail.getUserTypeId()));
		// get behaviors
		List<BehaviorSimple> behaviorSimpleList = JsonUtil
				.toJsonObjectArray(userModel.getBehaviorList(), BehaviorSimple.class);
		Map<String, BehaviorSimple> map = StreamApiUtils.toMap(behaviorSimpleList,
				BehaviorSimple::getId, behavior -> behavior);
		List<Behavior> behaviors = lcService.getBehaviorListByIds(map.keySet());
		// 补上时间
		for (Behavior behavior : behaviors) {
			behavior.setStartTime(map.get(behavior.getId()).getStartTime());
		}
		// sort
		BsUtil.sortBehaviorList(behaviors);
		detail.setBehaviors(behaviors);
		BsUtil.setHabits(detail, userModel.getAccessHabit());
		// create chart
		detail.setChart(new ChartResult());
		detail.getChart().setBehaviorList(behaviors);
		ChartUtil.createXy(detail.getChart());
		ChartUtil.createPie(detail.getChart());
		// 重复数据置空
		detail.getChart().setBehaviorList(null);
		return detail;
	}

	@Override
	public void create(UserModelAddBo addBo) {
		UserModel userModel = req2Entity(addBo);
		userModelMapper.insert(userModel);
	}

	@Override
	public void update(UserModelModifyBo modifyBo) {
		if (StringUtils.isBlank(modifyBo.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserModel userModel = req2Entity(modifyBo);
		userModelMapper.updateById(userModel);
	}

	@Override
	public void delete(List<String> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return;
		}
		userModelMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public ChartResult createChart(ChartInput chartInput) {
		Integer accessConfig = chartInput.getAccessConfig();
		ChartResult res = new ChartResult();

		if (accessConfig.equals(UserModel.AccessConfig.COSTUMER.getCode())) {
			BsUtil.sortBehaviorList(chartInput.getBehaviors());
		} else if (accessConfig.equals(UserModel.AccessConfig.RANDOM.getCode())) {
			RandomUtil.randomDistribute(chartInput);
		} else if (accessConfig.equals(UserModel.AccessConfig.NORMAL.getCode())) {
			RandomUtil.normalDistribute(chartInput);
		} else {
			throw new BusinessException(BsErrorCode.INVALID_ACCESS_CONF);
		}
		res.setBehaviorList(chartInput.getBehaviors());
		ChartUtil.createXy(res);
		ChartUtil.createPie(res);
		return res;
	}

	@Override
	public List<BehaviorWithVariable> getBehaviorsWithVariables(String userModelId) {
		UserModel userModel = getOrException(userModelId);
		List<BehaviorSimple> behaviorSimpleList = BsUtil
				.parseBehaviorList(userModel.getBehaviorList());
		List<String> behaviorIds = behaviorSimpleList.stream().map(BehaviorSimple::getId)
				.collect(Collectors.toList());
		List<LcBehaviorRspBo> rspBos = lcService.getLcBehaviorListByIds(behaviorIds, true);
		List<BehaviorWithVariable> res = rspBos.stream().map(this::createBehaviorWithVariable)
				.collect(Collectors.toList());
		return res;
	}

	private BehaviorWithVariable createBehaviorWithVariable(LcBehaviorRspBo rspBo) {
		BehaviorWithVariable behavior = new BehaviorWithVariable();
		behavior.setId(rspBo.getId());
		behavior.setName(rspBo.getFlowChartName());
		behavior.setStepCount(rspBo.getStepNum());
		behavior.getTag().add(rspBo.getTag());
		behavior.getApplication().add(rspBo.getApplication());
		if (CollectionUtils.isNotEmpty(rspBo.getVariables())) {
			for (LcVariableRspBo lcVariable : rspBo.getVariables()) {
				VariableSimple variableSimple = new VariableSimple();
				variableSimple.setId(lcVariable.getDataId());
				variableSimple.setName(lcVariable.getName());
				variableSimple.setValue(lcVariable.getVarValue());
				behavior.getVariables().add(variableSimple);
			}
		}
		return behavior;
	}

	private <T extends UserModelAddBo> UserModel req2Entity(T reqBo) {
		UserModel userModel = BeanUtils.copy(reqBo, UserModel.class);
		List<BehaviorSimple> behaviorSimpleList = BeanUtils.deepCopyList(reqBo.getBehaviors(),
				BehaviorSimple.class);
		// 保证顺序
		BsUtil.sortBehaviorList(behaviorSimpleList);
		userModel.setBehaviorList(
				JsonUtil.arrayToString(behaviorSimpleList, PropertyNamingStrategy.SnakeCase));
		userModel.setAccessHabit(JsonUtil.arrayToString(reqBo.getAccessHabit()));
		return userModel;
	}

	@Override
	public UserModel getOrException(String userModelId) {
		if (StringUtils.isBlank(userModelId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserModel userModel = userModelMapper.selectById(userModelId);
		if (null == userModel) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return userModel;
	}

	private UserModel getOrNull(String userModelId) {
		if (StringUtils.isBlank(userModelId)) {
			return null;
		}
		return userModelMapper.selectById(userModelId);
	}
}
