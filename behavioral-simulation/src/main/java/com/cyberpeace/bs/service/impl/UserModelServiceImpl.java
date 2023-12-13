package com.cyberpeace.bs.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.constant.BsConstants;
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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户模型Service实现
 *
 * @author SN
 * @date 2023/05/08
 */
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
	public ListRsp<UserModelBo> listUserModel(Page page, String userTypeId, String pattern,
			Integer accessOs) {
		// get user_behaviors
		IPage<UserModel> userBehaviorIPage = userModelMapper.pageUserBehaviors(page, userTypeId,
				pattern, accessOs);
		// get user types
		Set<String> userTypeIds = userBehaviorIPage.getRecords().stream()
				.map(UserModel::getUserTypeId).collect(Collectors.toSet());
		Map<String, String> userTypeMap = userTypeService.getIdNameMap(userTypeIds);
		// 转换数据
		userBehaviorIPage.convert((userModel -> {
			UserModelBo userModelBo = BeanUtils.copy(userModel, UserModelBo.class);
			BsUtil.setBehaviorStatistic(userModelBo, userModel.getBehaviorList());
			BsUtil.setHabits(userModelBo, userModel.getAccessHabit());
			userModelBo.setUserType(userTypeMap.get(userModel.getUserTypeId()));
			return userModelBo;
		}));
		return PageUtil.getListResult(userBehaviorIPage);
	}

	@Override
	public UserModelDetail getDetail(String resourceId) {
		// 查询用户模型
		UserModel userModel = getOrException(resourceId);
		UserModelDetail detail = BeanUtils.copy(userModel, UserModelDetail.class);
		detail.setUserType(userTypeService.getNameById(detail.getUserTypeId()));
		// get behaviors
		List<BehaviorSimple> behaviorSimpleList = JsonUtil
				.toJsonObjectArray(userModel.getBehaviorList(), BehaviorSimple.class);
		Map<String, BehaviorSimple> map = StreamApiUtils.toMap(behaviorSimpleList,
				BehaviorSimple::getId, behavior -> behavior);
		List<Behavior> behaviors;
		if (behaviorSimpleList.isEmpty()) {
			behaviors = Lists.newArrayList();
		} else {
			behaviors = lcService.getBehaviorListByIds(map.keySet());
		}
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
		// check name
		checkName(addBo.getUserName(), "");
		// 生成用户模型
		UserModel userModel = req2Entity(addBo);
		// 插入数据库
		userModelMapper.insert(userModel);
	}

	@Override
	public void update(UserModelModifyBo modifyBo) {
		// check name
		checkName(modifyBo.getUserName(), modifyBo.getResourceId());
		// 判断更新的实体是否有id
		if (StringUtils.isBlank(modifyBo.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		// 生成用户模型实体
		UserModel userModel = req2Entity(modifyBo);
		// 更新数据库
		userModelMapper.updateById(userModel);
	}

	@Override
	public void delete(List<String> resourceIds) {
		// 判断删除请求是否有效
		if (CollectionUtils.isEmpty(resourceIds)) {
			return;
		}
		// 删除指定用户模型
		userModelMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public ChartResult createChart(ChartInput chartInput) {
		// 获取访问配置
		Integer accessConfig = chartInput.getAccessConfig();
		ChartResult res = new ChartResult();

		if (accessConfig.equals(UserModel.AccessConfig.COSTUMER.getCode())) {
			// 自定义访问时间
			BsUtil.sortBehaviorList(chartInput.getBehaviors());
		} else if (accessConfig.equals(UserModel.AccessConfig.RANDOM.getCode())) {
			// 随机配置
			RandomUtil.randomDistribute(chartInput);
		} else if (accessConfig.equals(UserModel.AccessConfig.NORMAL.getCode())) {
			// 正态分布配置
			RandomUtil.normalDistribute(chartInput);
		} else {
			// 只支持以上3中配置方式
			throw new BusinessException(BsErrorCode.INVALID_ACCESS_CONF);
		}
		res.setBehaviorList(chartInput.getBehaviors());
		// 生成图表x轴数据
		ChartUtil.createXy(res);
		// 生成图表y轴数据
		ChartUtil.createPie(res);
		return res;
	}

	@Override
	public List<BehaviorWithVariable> getBehaviorsWithVariables(String userModelId) {
		// 查询用户模型
		UserModel userModel = getOrException(userModelId);
		// 获取用户模型包含的行为
		List<BehaviorSimple> behaviorSimpleList = BsUtil
				.parseBehaviorList(userModel.getBehaviorList());
		if (behaviorSimpleList.isEmpty()) {
			return Lists.newArrayList();
		}
		// 获取行为id列表
		List<String> behaviorIds = behaviorSimpleList.stream().map(BehaviorSimple::getId)
				.collect(Collectors.toList());
		// 查询行为具体数据以及行为变量数据
		List<LcBehaviorRspBo> rspBos = lcService.getLcBehaviorListByIds(behaviorIds, true);
		// 转换数据格式提供给前端展示
		List<BehaviorWithVariable> res = rspBos.stream().map(this::createBehaviorWithVariable)
				.collect(Collectors.toList());
		return res;
	}

	@Override
	public List<UserModelWithVariable> getBehaviorsBatchWithVariables(List<String> userModelIds) {
		// get user models
		List<UserModel> userModelList = userModelMapper.selectBatchIdsOrderByIdDesc(userModelIds);
		// 收集用户模型包含的行为，并按模型分类
		Map<String, List<String>> userBehaviorMap = Maps.newHashMap();
		Set<String> behaviorIdsAll = Sets.newHashSet();
		for (UserModel userModel : userModelList) {
			List<BehaviorSimple> behaviorSimpleList = BsUtil
					.parseBehaviorList(userModel.getBehaviorList());
			List<String> behaviorIds = behaviorSimpleList.stream().map(BehaviorSimple::getId)
					.collect(Collectors.toList());
			userBehaviorMap.put(userModel.getResourceId(), behaviorIds);
			behaviorIdsAll.addAll(behaviorIds);
		}
		// get behaviors and variables
		List<LcBehaviorRspBo> rspBos = Lists.newArrayList();
		if (!behaviorIdsAll.isEmpty()) {
			rspBos = lcService.getLcBehaviorListByIds(behaviorIdsAll, true);
		}
		Map<String, BehaviorWithVariable> behaviorMap = StreamApiUtils.toMap(rspBos,
				LcBehaviorRspBo::getId, this::createBehaviorWithVariable);
		// 组装响应数据
		List<UserModelWithVariable> res = Lists.newArrayList();
		for (UserModel userModel : userModelList) {
			UserModelWithVariable withVariable = new UserModelWithVariable();
			// 用户模型id
			withVariable.setUserModelId(userModel.getResourceId());
			// 用户名称
			withVariable.setUserName(userModel.getUserName());
			// 用户数量
			withVariable.setUserNum(userModel.getUserNum());
			// 是否限制带宽
			withVariable.setBandwidthLimited(userModel.getBandwidthLimited());
			// 带宽限制数值
			withVariable.setBandwidth(userModel.getBandwidth());
			// 填充变量
			List<String> behaviorIds = userBehaviorMap.get(userModel.getResourceId());
			for (String behaviorId : behaviorIds) {
				if (behaviorMap.containsKey(behaviorId)) {
					withVariable.getBehaviorList().add(behaviorMap.get(behaviorId));
				}
			}
			res.add(withVariable);
		}
		return res;
	}

	@Override
	public UserModel getOrException(String userModelId) {
		// 判断id是否合法
		if (StringUtils.isBlank(userModelId)) {
			throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
		}
		// 查询数据库
		UserModel userModel = userModelMapper.selectById(userModelId);
		// 结果为空，抛错
		if (null == userModel) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 结果不为空，返回数据
		return userModel;
	}

	/**
	 * 生成带变量的行为
	 *
	 * @param rspBo 远程响应数据
	 * @return {@code BehaviorWithVariable}
	 */
	private BehaviorWithVariable createBehaviorWithVariable(LcBehaviorRspBo rspBo) {
		BehaviorWithVariable behavior = new BehaviorWithVariable();
		// 行为id
		behavior.setId(rspBo.getId());
		// 行为名称
		behavior.setName(rspBo.getFlowChartName());
		// 行为步骤数
		behavior.setStepCount(rspBo.getStepNum());
		// 行为标签
		behavior.getTag().add(rspBo.getTag());
		// 行为应用
		behavior.getApplication().add(rspBo.getApplication());
		// 处理行为变量
		if (CollectionUtils.isNotEmpty(rspBo.getVariables())) {
			for (LcVariableRspBo lcVariable : rspBo.getVariables()) {
				VariableSimple variableSimple = new VariableSimple();
				variableSimple.setId(lcVariable.getDataId());
				variableSimple.setName(lcVariable.getName());
				// 去掉内网行为的host变量
				if (BsConstants.NetworkType.INTRANET.equals(rspBo.getNetModel())
						&& "host".equals(variableSimple.getName())) {
					variableSimple.setValue("");
				} else {
					variableSimple.setValue(lcVariable.getVarValue());
				}
				variableSimple.setNote(lcVariable.getNote());
				behavior.getVariables().add(variableSimple);
			}
		}
		return behavior;
	}

	/**
	 * 请求体转换为用户模型实体数据
	 * 适用于用户模型创建和更新
	 *
	 * @param reqBo 请求体数据
	 * @return {@code UserModel}
	 */
	private <T extends UserModelAddBo> UserModel req2Entity(T reqBo) {
		// 复制属性
		UserModel userModel = BeanUtils.copy(reqBo, UserModel.class);
		List<BehaviorSimple> behaviorSimpleList = BeanUtils.deepCopyList(reqBo.getBehaviors(),
				BehaviorSimple.class);
		// 保证顺序
		BsUtil.sortBehaviorList(behaviorSimpleList);
		// 行为列表设置为蛇形json字符串
		userModel.setBehaviorList(
				JsonUtil.arrayToString(behaviorSimpleList, PropertyNamingStrategy.SnakeCase));
		// 访问习惯列表转为json
		userModel.setAccessHabit(JsonUtil.arrayToString(reqBo.getAccessHabit()));
		return userModel;
	}

	/**
	 * 查询行为模型
	 * 查询得到返回查询结果，查询不到返回null
	 *
	 * @param userModelId 用户模型id
	 * @return {@code UserModel}
	 */
	private UserModel getOrNull(String userModelId) {
		if (StringUtils.isBlank(userModelId)) {
			return null;
		}
		return userModelMapper.selectById(userModelId);
	}

	/**
	 * 检查名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 */
	private void checkName(String name, String resourceId) {
		if (userModelMapper.existByName(name, resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}
}
