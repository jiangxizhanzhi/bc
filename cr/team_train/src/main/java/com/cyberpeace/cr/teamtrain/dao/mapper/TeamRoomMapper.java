package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;

/**
 * 团队房间Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface TeamRoomMapper extends BaseMapper<TeamRoom> {

	/**
	 * 获取团队房间响应由房间id
	 *
	 * @param resourceId 资源id
	 * @return {@code TeamRoomData }
	 */
	TeamRoomData getTeamRoomRspByRoomId(String resourceId);

	/**
	 * 列出团队聊天室
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<TeamRoomData> }
	 */
	IPage<TeamRoomData> listTeamRoom(Page<TeamRoomData> page, ListQuery listQuery);

	/**
	 * 查询由当地场景id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code TeamRoom }
	 */
	TeamRoom selectByLocalSceneId(String localSceneId);

	/**
	 * 获取训练计数
	 *
	 * @param roomIds         房间id列表
	 * @param completedStatus 完成状态
	 * @return {@code TrainingCount }
	 */
	TrainingCount getTrainingCount(@Param("roomIds") List<String> roomIds,
			@Param("completedStatus") Integer completedStatus);

	/**
	 * 查询未完成由id列表
	 *
	 * @param roomIds 房间id列表
	 * @param endCode 结束代号
	 * @return {@code List<TeamRoom> }
	 */
	List<TeamRoom> selectUnfinishedByIds(@Param("roomIds") List<String> roomIds,
			@Param("endCode") Integer endCode);

	/**
	 * 用于重名校验
	 *
	 * @param name
	 * @return
	 */
	Boolean existByName(@Param("name") String name);

	/**
	 * 实验场次统计
	 *
	 * @return
	 */
	List<Map<String, Object>> syNumStatistics();

	/**
	 * 科目场次统计
	 *
	 * @return
	 */
	List<Map<String, Object>> kmNumStatistics();

	/**
	 * 综合演训场次统计
	 *
	 * @return
	 */
	List<Map<String, Object>> zhyxNumStatistics();

	/**
	 * 实验人数统计
	 *
	 * @return
	 */
	List<Map<String, Object>> syPersonNumStatistics();

	/**
	 * 科目人数统计
	 *
	 * @return
	 */
	List<Map<String, Object>> kmPersonNumStatistics();

	/**
	 * 综合演训人数统计
	 *
	 * @return
	 */
	List<Map<String, Object>> zhyxPersonNumStatistics();

	/**
	 * 综合演训训练总分统计
	 *
	 * @return
	 */
	List<Map<String, Object>> totalScoreStatistics();

	/**
	 * 综合演训实验总分统计
	 *
	 * @return
	 */
	List<Map<String, Object>> totalScoreSyStatistics();

	/**
	 * 综合演训科目总分统计
	 *
	 * @return
	 */
	List<Map<String, Object>> totalScoreKmStatistics();

	/**
	 * 综合演训综合演训总分统计
	 *
	 * @return
	 */
	List<Map<String, Object>> totalScoreZhyxStatistics();


	/**
	 * 根据roomIds查询所有列表
	 *
	 * @param roomIds 房间id列表
	 * @return
	 */
	List<TeamRoom> selectAllByIds(@Param("roomIds") List<String> roomIds);
}
