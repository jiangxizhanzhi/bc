package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 训练计划Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TrainPlanMapper` 的接口，继承自 `BaseMapper<TrainPlan>`。该接口使用了 MyBatis-Plus 框架，用于数据库操作。以下是代码的主要内容和功能分析： 
 * ### 主要类和方法 
 * 1. **类定义** 
 * ```java 
 * @Mapper 
 * public interface TrainPlanMapper extends BaseMapper<TrainPlan> { 
 * ``` 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `extends BaseMapper<TrainPlan>` 表示该接口继承了 MyBatis-Plus 提供的基础映射器，提供了基本的 CRUD 操作。 
 * 2. **方法定义** 
 * - **查询特定训练计划** 
 * ```java 
 * TrainPlan selectByName(@Param("planName") String planName, @Param("userId") String userId); 
 * ``` 
 * - 根据训练计划名称和用户ID查询一个训练计划。 
 * - **根据用户ID查询所有训练计划** 
 * ```java 
 * List<TrainPlan> selectByUser(@Param("userId") String userId); 
 * ``` 
 * - 根据用户ID查询该用户的全部训练计划。 
 * - **查询所有训练计划** 
 * ```java 
 * List<TrainPlan> all(); 
 * ``` 
 * - 查询所有的训练计划。 
 * - **分页查询训练计划** 
 * ```java 
 * IPage<TrainPlanRsp> listTrainPlan( 
 * Page page, 
 * String pattern, 
 * String organizationId, 
 * String userId, 
 * boolean isSuperUser, 
 * boolean isAdmin, 
 * List<String> courseIds 
 * ); 
 * ``` 
 * - 分页查询训练计划，并支持多种过滤条件（如模式、组织ID、用户ID、是否超级用户、是否管理员、课程ID列表）。 
 * - 返回结果为 `IPage<TrainPlanRsp>` 类型，即分页响应对象。 
 * ### 总结 
 * - **功能**：该接口主要用于对 `TrainPlan` 实体进行增删改查操作，并提供了一些额外的功能，如分页查询和基于多个条件的查询。 
 * - **技术栈**：使用了 MyBatis-Plus 和 MyBatis 框架来简化数据库操作。 
 * - **输入输出**：方法参数包括字符串、布尔值和列表类型，返回值主要是 `TrainPlan` 对象或其列表，以及分页响应对象 `IPage<TrainPlanRsp>`。 
 * 通过这些方法，可以灵活地管理和查询训练计划数据。 
**/
@Mapper
public interface TrainPlanMapper extends BaseMapper<TrainPlan> {

	/**
	 * 查询由名称
	 *
	 * @param planName 计划名称
	 * @param userId   用户id
	 * @return {@code TrainPlan }
	 * @throws
	 * @see String
	 * @see String
	 * @see TrainPlan
	 */
	TrainPlan selectByName(@Param("planName") String planName, @Param("userId") String userId);

	/**
	 * 查询由用户
	 *
	 * @param userId 用户id
	 * @return {@code List<TrainPlan> }
	 * @throws
	 * @see String
	 * @see List<TrainPlan>
	 */
	List<TrainPlan> selectByUser(@Param("userId") String userId);

	/**
	 * 都
	 *
	 * @return {@code List<TrainPlan> }
	 * @throws
	 * @see List<TrainPlan>
	 */
	List<TrainPlan> all();

	/**
	 * 列表训练计划
	 *
	 * @param page           分页
	 * @param pattern        模式
	 * @param organizationId 组织id
	 * @param userId         用户id
	 * @param isSuperUser    是超级用户
	 * @param isAdmin        是管理员
	 * @param courseIds      课程id列表
	 * @return {@code IPage<TrainPlanRsp> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see String
	 * @see String
	 * @see boolean
	 * @see boolean
	 * @see List<String>
	 * @see IPage<TrainPlanRsp>
	 */
	IPage<TrainPlanRsp> listTrainPlan(Page page, String pattern, String organizationId, String userId, boolean isSuperUser, boolean isAdmin, List<String> courseIds);
}

