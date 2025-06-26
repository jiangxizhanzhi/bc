package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;

/**
 * 红蓝对抗房间Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface RbRoomMapper extends BaseMapper<RbRoom> {

	/**
	 * 查询全部
	 *
	 * @return {@code List<RbRoom> }
	 */
	List<RbRoom> selectAll();

	/**
	 * 计数全部
	 *
	 * @return {@code Integer }
	 */
	Integer countAll();

	/**
	 * 获取红蓝对抗房间数据由房间id
	 *
	 * @param resourceId 资源id
	 * @return {@code RbRoomData }
	 */
	RbRoomData getRbRoomDataByRoomId(String resourceId);

	/**
	 * 列表红蓝对抗房间
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<RbRoomData> }
	 */
	IPage<RbRoomData> listRbRoom(Page<RbRoomData> page, ListQuery listQuery);

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
	 * @param roomIds   房间id列表
	 * @param endStatus 结束状态
	 * @return {@code List<RbRoom> }
	 */
	List<RbRoom> selectUnfinishedByIds(@Param("roomIds") List<String> roomIds,
			@Param("endStatus") Integer endStatus);

	/**
	 * 计数由名称
	 *
	 * @param name 名称
	 * @return {@code Integer }
	 */
	Integer countByName(@Param("name") String name);

	/**
	 * 查询全部运行
	 *
	 * @return {@code List<RbSituation> }
	 */
	List<RbSituation> selectAllRunning();

	/**
	 * 更新当地场景id
	 *
	 * @param roomId       房间id
	 * @param localSceneId 当地场景id
	 * @throws
	 * @see String
	 * @see String
	 */
	void updateLocalSceneId(@Param("roomId") String roomId, @Param("localSceneId") String localSceneId);

	/**
	 * 更新训练状态
	 *
	 * @param roomId         房间id
	 * @param trainingStatus 训练状态
	 * @throws
	 * @see String
	 * @see Integer
	 */
	void updateTrainingStatus(String roomId, Integer trainingStatus);

	/**
	 * 更新复盘场景id
	 *
	 * @param resourceId    资源id
	 * @param replaySceneId 复盘场景id
	 * @param sceneIdRecord 场景id记录
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void updateReplaySceneId(@Param("resourceId") String resourceId,
			@Param("replaySceneId") String replaySceneId,
			@Param("sceneIdRecord") String sceneIdRecord);

    RbRoom getRbRoomByUniteTaskId(@Param("taskId") String taskId);
}
