package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;

/**
 * cr任务提交映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface CrTaskSubmitMapper extends BaseMapper<CrTaskSubmit> {

	/**
	 * 通过房间id获取
	 *
	 * @param roomId 房间id
	 * @return {@link List}<{@link CrTaskSubmit}>
	 */
	List<CrTaskSubmit> selectByRoomId(String roomId);

	/**
	 * 通过任务id获取最新的
	 *
	 * @param taskId 任务id
	 * @return {@link CrTaskSubmit}
	 */
	CrTaskSubmit selectLastByTaskId(String taskId);

	/**
	 * cr任务列表提交
	 *
	 * @param page      页面
	 * @param listQuery 列表查询
	 * @return {@link IPage}<{@link MissionEvaluationContext}>
	 */
	IPage<MissionEvaluationContext> listCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

	/**
	 * rb cr任务提交列表
	 *
	 * @param page      页面
	 * @param listQuery 列表查询
	 * @return {@link IPage}<{@link MissionEvaluationContext}>
	 */
	IPage<MissionEvaluationContext> listRbCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

	/**
	 * 通过任务获取顺序提交
	 *
	 * @param resourceId 资源id
	 * @return {@link List}<{@link CrTaskSubmit}>
	 */
	List<CrTaskSubmit> selectOrderedSubmitsByTask(String resourceId);

	/**
	 * 通过房间id和用户获取
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id
	 * @return {@link List}<{@link CrTaskSubmit}>
	 */
	List<CrTaskSubmit> selectByRoomIdAndUsers(String roomId, List<String> userIds);
}
