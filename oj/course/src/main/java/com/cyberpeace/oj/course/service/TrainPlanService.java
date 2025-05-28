/**
 * 这段代码定义了一个名为 `TrainPlanService` 的接口，它继承自 `IService<TrainPlan>`。这个接口主要用于处理与训练计划相关的业务逻辑。以下是对其主要部分的分析： 
 * ### 接口定义 
 * - **接口名称**: `TrainPlanService` 
 * - **继承**: `com.baomidou.mybatisplus.extension.service.IService<TrainPlan>` 
 * - **作者**: SN 
 * - **版本**: 1.0.0 
 * - **日期**: 2024/11/08 
 * ### 方法定义 
 * #### 1. `trainPlanList(UserInfo userInfo)` 
 * - **描述**: 查询可访问的训练计划列表。 
 * - **参数**: 
 * - `UserInfo userInfo`: 用户信息对象，用于确定用户是否有权限访问某些训练计划。 
 * - **返回值**: 
 * - `List<TrainPlanBo>`: 返回一个包含多个 `TrainPlanBo` 对象的列表，这些对象表示训练计划的基本信息。 
 * #### 2. `page(TrainPlanReq trainPlanReq, UserInfo userInfo)` 
 * - **描述**: 分页查询训练计划。 
 * - **参数**: 
 * - `TrainPlanReq trainPlanReq`: 包含分页请求参数的对象。 
 * - `UserInfo userInfo`: 用户信息对象，用于确定用户是否有权限访问某些训练计划。 
 * - **返回值**: 
 * - `IPage<TrainPlanRsp>`: 返回一个分页结果对象，其中包含符合条件的 `TrainPlanRsp` 对象列表。 
 * #### 3. `checkNameRepeat(String name, String id, String userId)` 
 * - **描述**: 检查训练计划名称是否重复。 
 * - **参数**: 
 * - `String name`: 训练计划的名称。 
 * - `String id`: 训练计划的唯一标识符（ID）。 
 * - `String userId`: 用户的唯一标识符。 
 * - **返回值**: 
 * - 该方法没有返回值，但会抛出异常来指示名称重复的情况。 
 * ### 总结 
 * - `TrainPlanService` 接口提供了一系列与训练计划相关的操作，包括获取训练计划列表、分页查询以及检查名称重复。 
 * - 这些方法的设计考虑了用户权限管理和数据验证的需求。 
 * - 使用泛型 `IPage<TrainPlanRsp>` 可以方便地进行分页查询，并且可以灵活地调整返回的数据格式。 
 * - 接口中的方法注释清晰，有助于开发者理解每个方法的功能和使用场景。 
**/
package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

import java.util.List;

/**
 * 训练计划Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface TrainPlanService extends IService<TrainPlan> {

	/**
	 * 查询可访问的训练计划列表
	 *
	 * @param userInfo
	 * @return
	 * @throws
	 * @see UserInfo
	 * @see List<TrainPlanBo>
	 */
	List<TrainPlanBo> trainPlanList(UserInfo userInfo);

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
	IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo);

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
	void checkNameRepeat(String name, String id, String userId);
}
