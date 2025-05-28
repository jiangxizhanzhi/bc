/**
 * 这段代码是一个Java类，实现了名为`TrainPlanServiceImpl`的服务层接口。这个类的主要功能是处理与训练计划相关的业务逻辑。以下是代码的简要分析： 
 * ### 类定义和依赖注入 
 * - `TrainPlanServiceImpl` 继承了 `ServiceImpl<TrainPlanMapper, TrainPlan>`，并实现了 `TrainPlanService` 接口。 
 * - 使用了 Lombok 的 `@Slf4j` 注解来自动创建一个日志记录器。 
 * - 通过 `@Autowired` 注入了以下依赖： 
 * - `TrainPlanMapper`：用于操作数据库中的训练计划表。 
 * - `RemoteUserService`：远程用户服务，用于获取用户信息。 
 * - `CourseMapper`：课程映射器，用于获取课程相关信息。 
 * ### 方法实现 
 * 1. **`trainPlanList` 方法** 
 * - 根据用户是否为超级用户，返回所有训练计划或仅返回当前用户的训练计划。 
 * - 使用 `StreamApiUtils.map` 方法将实体对象列表转换为 `TrainPlanBo` 对象列表。 
 * 2. **`page` 方法** 
 * - 实现分页查询训练计划，并根据用户权限和组织ID进行过滤。 
 * - 获取用户信息，并填充到查询结果中。 
 * - 遍历查询结果，调用 `remoteUserService` 获取创建者的用户名，并将其设置到 `TrainPlanRsp` 对象中。 
 * 3. **`checkNameRepeat` 方法** 
 * - 检查训练计划名称是否重复。 
 * - 如果名称已存在且 ID 不匹配，则抛出 `BusinessException` 异常。 
 * 4. **`entityToBo` 方法** 
 * - 将 `TrainPlan` 实体对象转换为 `TrainPlanBo` 对象。 
 * - 获取创建者的名字并设置到 `TrainPlanBo` 对象中。 
 * ### 总结 
 * - `TrainPlanServiceImpl` 类主要负责处理与训练计划相关的业务逻辑。 
 * - 该类使用 MyBatis-Plus 进行数据访问，并通过远程服务获取用户信息。 
 * - 通过 `trainPlanList` 和 `page` 方法提供了训练计划的列表和分页查询功能。 
 * - `checkNameRepeat` 方法确保训练计划名称的唯一性。 
 * - `entityToBo` 方法负责数据转换，将实体对象转换为业务对象。 
 * 这段代码展示了如何在 Spring Boot 项目中实现服务层逻辑，并结合 MyBatis-Plus 进行数据库操作。 
**/
package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 训练计划Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see TrainPlanService
 * @since 1.0.0
 */
@Slf4j
@Service
public class TrainPlanServiceImpl extends ServiceImpl<TrainPlanMapper, TrainPlan> implements TrainPlanService {

	/**
	 * 训练计划Mapper
	 *
	 * @see TrainPlanMapper
	 */
	@Autowired
	private TrainPlanMapper trainPlanMapper;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 训练计划列表
	 *
	 * @param userInfo 用户信息
	 * @return {@code List<TrainPlanBo> }
	 * @throws
	 * @see UserInfo
	 * @see List<TrainPlanBo>
	 */
	@Override
	public List<TrainPlanBo> trainPlanList(UserInfo userInfo) {
		List<TrainPlan> trainPlans = userInfo.isSuperuser() ? trainPlanMapper.all()
				: trainPlanMapper.selectByUser(userInfo.getUserId());
		return StreamApiUtils.map(trainPlans, trainPlan -> entityToBo(trainPlan, userInfo));
	}

	/**
	 * 分页
	 *
	 * @param trainPlanReq 训练计划请求体
	 * @param userInfo     用户信息
	 * @return {@code IPage<TrainPlanRsp> }
	 * @throws
	 * @see TrainPlanReq
	 * @see UserInfo
	 * @see IPage<TrainPlanRsp>
	 */
	@Override
	public IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo) {

		Page page = trainPlanReq.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(AuthContextUtils.constructAuth(userInfo,
				false));
		IPage<TrainPlanRsp> result = trainPlanMapper.listTrainPlan(page, trainPlanReq.getPattern(), trainPlanReq.getOrganizationId(),
				userInfo.getUserId(), userInfo.isSuperuser(), userInfo.isAdmin(), courseIds);

		for (TrainPlanRsp trainPlanRsp : result.getRecords()) {
			UserInfo user = remoteUserService.getById(trainPlanRsp.getCreateUserId());
			if (null != user) {
				trainPlanRsp.setCreateUser( user.getName() );
			}
		}
		return result;
	}

	/**
	 * 检查名称重复
	 *
	 * @param name   名称
	 * @param id     id
	 * @param userId 用户id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	@Override
	public void checkNameRepeat(String name, String id, String userId) {
		LambdaQueryWrapper<TrainPlan> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TrainPlan::getName, name);
		queryWrapper.eq(TrainPlan::getCreateUserId, userId);

		if (StringUtils.isNotBlank(id)) {
			queryWrapper.ne(TrainPlan::getResourceId, id);
		}
		List<TrainPlan> assessModel = trainPlanMapper.selectList(queryWrapper);
		if (null != assessModel && assessModel.size()>0) {
			log.error("[{}] name repeat", StreamApiUtils.getColumn(assessModel, TrainPlan::getName).toString());
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

	/**
	 * 数据转换
	 *
	 * @param trainPlan 训练计划
	 * @param userInfo  用户信息
	 * @return {@code TrainPlanBo }
	 * @throws
	 * @see TrainPlan
	 * @see UserInfo
	 * @see TrainPlanBo
	 */
	private TrainPlanBo entityToBo(TrainPlan trainPlan, UserInfo userInfo) {
		TrainPlanBo planBo = BeanUtils.copy(trainPlan, TrainPlanBo.class);
		planBo.setTrainPlanName(trainPlan.getName());

		UserInfo user = remoteUserService.getById(trainPlan.getCreateUserId());
		if (null != user) {
			planBo.setUsername(userInfo.isSuperuser() ? user.getName() : CommonConstant.EMPTY_STRING);
		}
		return planBo;
	}
}
