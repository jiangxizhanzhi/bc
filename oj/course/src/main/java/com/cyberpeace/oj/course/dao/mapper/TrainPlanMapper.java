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
 * @date 2024/11/14
 */
@Mapper
public interface TrainPlanMapper extends BaseMapper<TrainPlan> {

	/**
	 * 查询由名称
	 *
	 * @param planName 计划名称
	 * @param userId   用户id
	 * @return {@code TrainPlan }
	 */
	TrainPlan selectByName(@Param("planName") String planName, @Param("userId") String userId);

	/**
	 * 查询由用户
	 *
	 * @param userId 用户id
	 * @return {@code List<TrainPlan> }
	 */
	List<TrainPlan> selectByUser(@Param("userId") String userId);

	/**
	 * 全部
	 *
	 * @return {@code List<TrainPlan> }
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
	 * @return {@code IPage<TrainPlanRsp> }
	 */
	IPage<TrainPlanRsp> listTrainPlan(Page<TrainPlanRsp> page, String pattern, String organizationId, String userId, boolean isSuperUser, boolean isAdmin, List<String> courseIds);
}
