package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RB房间Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface RbRoomMapper extends BaseMapper<RbRoom> {

	/**
	 * 查询都
	 *
	 * @return {@code List<RbRoom> }
	 * @throws
	 * @see List<RbRoom>
	 */
	List<RbRoom> selectAll();

	/**
	 * 计数都
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	Integer countAll();

	/**
	 * 获取RB房间数据由房间id
	 *
	 * @param resourceId 资源id
	 * @return {@code RbRoomData }
	 * @throws
	 * @see String
	 * @see RbRoomData
	 */
	RbRoomData getRbRoomDataByRoomId(String resourceId);

	/**
	 * 列出 RB 房间
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<RbRoomData> }
	 * @throws
	 * @see Page
	 * @see ListQuery
	 * @see IPage<RbRoomData>
	 */
	IPage<RbRoomData> listRbRoom(Page page, ListQuery listQuery);

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
	 * @param roomIds   房间id列表
	 * @param endStatus 结束状态
	 * @return {@code List<RbRoom> }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 * @see List<RbRoom>
	 */
	List<RbRoom> selectUnfinishedByIds(@Param("roomIds") List<String> roomIds,
			@Param("endStatus") Integer endStatus);

	/**
	 * 计数由名称
	 *
	 * @param name 名称
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer countByName(@Param("name") String name);

	/**
	 * 查询都运行
	 *
	 * @return {@code List<RbSituation> }
	 * @throws
	 * @see List<RbSituation>
	 */
	List<RbSituation> selectAllRunning();
}
