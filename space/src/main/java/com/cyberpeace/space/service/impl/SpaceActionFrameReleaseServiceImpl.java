package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.*;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 空间行动框架下发Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see ServiceImpl
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 项目背景与功能 
 * 该代码是一个基于 `MyBatis-Plus` 的服务实现类，用于处理与“空间行动框架”相关的业务逻辑。主要涉及以下功能： 
 * 1. **创建行为框架发布记录**：根据请求参数生成并保存行为框架的发布信息。 
 * 2. **获取策略信息**：根据应用 ID 和步骤，查询并返回相关策略信息。 
 * 3. **获取行为依赖信息**：通过资源 ID 查询并返回行为依赖的相关信息，并更新步骤状态。 
 * 4. **检查是否已发布**：验证某个阶段是否已经被发布。 
 * --- 
 * #### 核心类与方法 
 * 1. **类结构** 
 * - 类名为 `SpaceActionFrameReleaseServiceImpl`，继承自 `ServiceImpl`，实现了 `SpaceActionFrameReleaseService` 接口。 
 * - 使用了多个 Mapper（如 `SpaceActionFrameReleaseMapper`、`SpaceActionFrameApplicationMapper` 等）和业务服务（如 `SpaceActionFrameApplicationService`、`SpaceActionFrameService`），通过依赖注入完成数据操作。 
 * 2. **核心方法** 
 * - **`create(SpaceActionFrameReleaseReq req)`** 
 * - 功能：根据请求参数创建行为框架的发布记录。 
 * - 流程： 
 * 1. 验证是否已经存在相同的发布记录（调用 `checkRelease` 方法）。 
 * 2. 将请求参数转换为实体对象（使用工具类 `BeanUtils`）。 
 * 3. 查询对应的 `SpaceActionFrameApplication` 实体，并设置到发布记录中。 
 * 4. 根据步骤名称匹配对应的策略信息，并保存发布记录。 
 * - **`getTactics(String applicationId)`** 
 * - 功能：获取指定应用 ID 的策略信息。 
 * - 流程： 
 * 1. 查询 `SpaceActionFrameApplication` 实体。 
 * 2. 分组查询 `SpaceActionFrameOrder` 数据，按步骤分组。 
 * 3. 构建 `SpaceActionFrameOrderBo` 对象列表，包含策略名称、类型、状态等信息。 
 * 4. 返回策略信息列表。 
 * - **`getApplicationBo(String resourceId)`** 
 * - 功能：获取指定资源 ID 的行为依赖信息。 
 * - 流程： 
 * 1. 查询 `SpaceActionFrameApplication` 实体，并转换为 `SpaceActionFrameApplicationBo` 对象。 
 * 2. 更新行为依赖的步骤状态： 
 * - 查询订单信息并分组。 
 * - 遍历步骤和策略，更新状态并映射名称。 
 * 3. 返回完整的 `SpaceActionFrameApplicationBo` 对象。 
 * - **`checkRelease(String applicationId, String stage)`** 
 * - 功能：检查指定应用 ID 和阶段是否已经发布。 
 * - 流程： 
 * 1. 构造查询条件，判断是否存在相同的应用 ID 和阶段的记录。 
 * 2. 如果存在记录，则抛出异常，提示该阶段已被发布。 
 * --- 
 * #### 技术特点 
 * 1. **工具类的使用** 
 * - 使用了 `BeanUtils` 进行对象转换。 
 * - 使用了 `StreamApiUtils` 提供的流式操作（如分组、转 Map 等）。 
 * - 使用了 `JsonUtil` 处理 JSON 数据。 
 * 2. **异常处理** 
 * - 在关键操作中使用了 `BusinessException` 抛出自定义异常，例如未找到资源或重复发布的场景。 
 * 3. **依赖注入** 
 * - 使用 `@Autowired` 注入多个 Mapper 和业务服务，便于解耦和复用。 
 * 4. **日志记录** 
 * - 使用 `lombok` 的 `@Slf4j` 注解，方便记录日志信息。 
 * --- 
 * #### 总结 
 * 该代码实现了行为框架的发布管理功能，包括创建、查询和状态更新等操作。代码结构清晰，功能模块化，使用了多种技术手段提升开发效率和可维护性。同时，通过异常处理和工具类的使用，确保了代码的健壮性和可读性。 
**/
@Service
@Slf4j
public class SpaceActionFrameReleaseServiceImpl extends ServiceImpl<SpaceActionFrameReleaseMapper, SpaceActionFrameRelease> implements SpaceActionFrameReleaseService {

	@Autowired
	private SpaceActionFrameApplicationMapper applicationMapper;

	@Autowired
	private SpaceTacticDefenseMapper tacticDefenseMapper;

	@Autowired
	private SpaceTacticAttackMapper tacticAttackMapper;

	@Autowired
	private SpaceActionFrameOrderMapper orderMapper;

	@Autowired
	private SpaceActionFrameApplicationService applicationService;

	@Autowired
	private SpaceActionFrameService frameService;

	/**
	 * 创建
	 *
	 * @param req 请求体
	 * @throws
	 * @see SpaceActionFrameReleaseReq
	 */
	@Override
	public void create(SpaceActionFrameReleaseReq req) {
		this.checkRelease(req.getApplicationId(), req.getStep());
		SpaceActionFrameRelease entity = BeanUtils.copy(req, SpaceActionFrameRelease.class);
		SpaceActionFrameApplication application = applicationMapper
				.selectById(req.getApplicationId());
		entity.setFrameName(application.getName());
		entity.setRvbId(application.getRvbId());

		ActionFrameArrangeBo frame = application.getArrangeBo();
		for (ActionFrameStep step : frame.getSteps()) {
			if (req.getStep().equals(step.getName())) {
				entity.setTactics(JsonUtil.arrayToString(step.getTactics()));
			}
		}
		this.save(entity);
	}

	/**
	 * 获取策略
	 *
	 * @param applicationId 行为依赖id
	 * @return {@code List<SpaceActionFrameOrderBo> }
	 * @throws
	 * @see String
	 * @see List<SpaceActionFrameOrderBo>
	 */
	@Override
	public List<SpaceActionFrameOrderBo> getTactics(String applicationId) {
		List<SpaceActionFrameOrderBo> res = Lists.newArrayList();
		SpaceActionFrameApplication application = applicationMapper.selectById(applicationId);
		if(null == application){
			return res;
		}
		ActionFrameArrangeBo actionFrameArrangeBo = application.getArrangeBo();

		// order complete stats
		LambdaQueryWrapper<SpaceActionFrameOrder> orderWrapper = new LambdaQueryWrapper<>();
		orderWrapper.eq(SpaceActionFrameOrder::getApplicationId, application.getResourceId());
		Map<String, List<SpaceActionFrameOrder>> orderMap = StreamApiUtils
				.groupBy(orderMapper.selectList(orderWrapper), SpaceActionFrameOrder::getStep);
		Map<String, String> userMap = applicationService.getUserMap(application.getRvbId(),
				SpaceCommonConstant.RvbUserGroup.WORKER);
		Map<String, String> tacticNameMap = frameService.getTacticNameMap(actionFrameArrangeBo);
		
		// keep order of step & tactic
		for (ActionFrameStep step : actionFrameArrangeBo.getSteps()) {
			List<SpaceActionFrameOrder> orderList = orderMap.getOrDefault(step.getResourceId(),
					Lists.newArrayList());
			Map<String, SpaceActionFrameOrder> map = StreamApiUtils.toMap(orderList,
					SpaceActionFrameOrder::getTacticId, a -> a);
			for (ActionFrameTactic tactic : step.getTactics()) {
				SpaceActionFrameOrderBo bo = new SpaceActionFrameOrderBo();
				bo.setFrameName(application.getName());
				bo.setTacticName(tacticNameMap.containsKey(tactic.getResourceId())? tacticNameMap.get(tactic.getResourceId()):tactic.getName());
				bo.setTacticId(tactic.getResourceId());
				bo.setTacticType(tactic.getType());
				bo.setStepName(step.getName());
				bo.setStepId(step.getResourceId());
				bo.setApplicationId(applicationId);
				if(map.containsKey(tactic.getResourceId())){
					bo.setResourceId(map.get(tactic.getResourceId()).getResourceId());
					bo.setUserId(map.get(tactic.getResourceId()).getUserId());
					bo.setUserName(userMap.get(bo.getUserId()));
					bo.setReleased(true);
					bo.setStatus(map.get(tactic.getResourceId()).getStatus());
				}
				res.add(bo);
			}
		}
		return res;
	}

	/**
	 * 获取行为依赖Bo
	 *
	 * @param resourceId 资源id
	 * @return {@code SpaceActionFrameApplicationBo }
	 * @throws
	 * @see String
	 * @see SpaceActionFrameApplicationBo
	 */
	@Override
	public SpaceActionFrameApplicationBo getApplicationBo(String resourceId) {
		SpaceActionFrameApplication application = applicationService.getById(resourceId);
		if (null == application) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SpaceActionFrameApplicationBo bo = application.entity2Bo(Maps.newHashMap());
		// 重置进度
		bo.getFrame().resetStatus();

//		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
//		wrapper.eq(SpaceActionFrameRelease::getApplicationId, application.getResourceId());
//		List<SpaceActionFrameRelease> releaseList = this.list(wrapper);
//		Map<String, SpaceActionFrameRelease> releaseMap = StreamApiUtils.toMap(releaseList,
//				SpaceActionFrameRelease::getStep, a -> a);
		
		// order complete status
		LambdaQueryWrapper<SpaceActionFrameOrder> orderWrapper = new LambdaQueryWrapper<>();
		orderWrapper.eq(SpaceActionFrameOrder::getApplicationId, application.getResourceId());
		Map<String, List<SpaceActionFrameOrder>> orderMap = StreamApiUtils
				.groupBy(orderMapper.selectList(orderWrapper), SpaceActionFrameOrder::getStep);
		Map<String, String> tacticNameMap = frameService.getTacticNameMap(bo.getFrame());
		for (ActionFrameStep step : bo.getFrame().getSteps()) {
			if (!orderMap.containsKey(step.getResourceId())) {
//				if (releaseMap.containsKey(step.getName())) {
//					step.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);
//				}
				continue;
			}
			int complete = 0;
			List<SpaceActionFrameOrder> orderList = orderMap.get(step.getResourceId());
			Map<String, SpaceActionFrameOrder> map = StreamApiUtils.toMap(orderList,
					SpaceActionFrameOrder::getTacticId, o -> o);
			for (ActionFrameTactic tactic : step.getTactics()) {
				if (map.containsKey(tactic.getResourceId())) {
					SpaceActionFrameOrder order = map.get(tactic.getResourceId());
					tactic.setStatus(order.getStatus());
					if (Objects.equals(order.getStatus(),
							SpaceCommonConstant.OrderStatus.FINISHED)) {
						complete++;
					}
				}
				if (tacticNameMap.containsKey(tactic.getResourceId())) {
					tactic.setName(tacticNameMap.get(tactic.getResourceId()));
				}
			}
			if (complete == step.getTactics().size()) {
				step.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
			} else {
				step.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);
			}
		}
		return bo;

	}

	/**
	 * 检查下发
	 *
	 * @param applicationId 行为依赖id
	 * @param stage         阶段
	 * @throws
	 * @see String
	 * @see String
	 */
	public void checkRelease(String applicationId, String stage) {
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		wrapper.eq(SpaceActionFrameRelease::getStep, stage);
		if (this.count(wrapper) > 0) {
			throw new BusinessException(SpaceError.STAGE_RELEASED);
		}
	}
}

