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
