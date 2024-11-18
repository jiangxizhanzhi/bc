package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

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
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface TeamRoomMapper extends BaseMapper<TeamRoom> {

	/**
	 * 获取团队房间响应由房间id
	 *
	 * @param resourceId 资源id
	 * @return {@code TeamRoomData }
	 * @throws
	 * @see String
	 * @see TeamRoomData
	 */
	TeamRoomData getTeamRoomRspByRoomId(String resourceId);

	/**
	 * 列出团队聊天室
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<TeamRoomData> }
	 * @throws
	 * @see Page
	 * @see ListQuery
	 * @see IPage<TeamRoomData>
	 */
	IPage<TeamRoomData> listTeamRoom(Page page, ListQuery listQuery);

	/**
	 * 查询由当地场景id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code TeamRoom }
	 * @throws
	 * @see String
	 * @see TeamRoom
	 */
	TeamRoom selectByLocalSceneId(String localSceneId);

	/**
	 * 获取训练计数
	 *
	 * @param roomIds         房间id列表
	 * @param completedStatus 完成状态
	 * @return {@code TrainingCount }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 * @see TrainingCount
	 */
	TrainingCount getTrainingCount(@Param("roomIds") List<String> roomIds,
			@Param("completedStatus") Integer completedStatus);

	/**
	 * 查询未完成由id列表
	 *
	 * @param roomIds 房间id列表
	 * @param endCode 结束代号
	 * @return {@code List<TeamRoom> }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 * @see List<TeamRoom>
	 */
	List<TeamRoom> selectUnfinishedByIds(@Param("roomIds") List<String> roomIds,
			@Param("endCode") Integer endCode);

	/**
	 * 用于重名校验
	 *
	 * @param name
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean existByName(@Param("name") String name);
}
