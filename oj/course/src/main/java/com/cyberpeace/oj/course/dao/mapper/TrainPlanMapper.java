package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.controller.res.TrainPlanWebRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;

/**
 * 训练计划Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
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
	 * 全部
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
	 * @param isSuperUser    是否超用户
	 * @param isAdmin        是否管理
	 * @param courseIds      课程id列表
	 * @param status
	 * @param defaultTaskSetName
	 * @return {@code IPage<TrainPlanRsp> }
	 * @throws
	 * @see Page<TrainPlanRsp>
	 * @see String
	 * @see String
	 * @see String
	 * @see boolean
	 * @see boolean
	 * @see List<String>
	 * @see IPage<TrainPlanRsp>
	 */
	IPage<TrainPlanRsp> listTrainPlan(@Param("page") Page<TrainPlanRsp> page,
			@Param("pattern") String pattern, @Param("organizationId") String organizationId,
			@Param("userId") String userId, @Param("isSuperUser") boolean isSuperUser,
			@Param("isAdmin") boolean isAdmin, @Param("courseIds") List<String> courseIds,
			@Param("status") Integer status,
			@Param("defaultTaskSetName") String defaultTaskSetName);

	IPage<TrainPlanWebRsp> listTrainWebPlan(@Param("page") Page<TrainPlanWebRsp> page,
			@Param("pattern") String pattern, @Param("organizationId") String organizationId,
			@Param("courseIds") List<String> courseIds, @Param("planStatus") Integer planStatus,
			@Param("defaultTaskSetName") String defaultTaskSetName);
}
