package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.service.context.DynamicData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 靶场任务日志Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface CrTaskLogMapper extends BaseMapper<CrTaskLog> {

	/**
	 * 查询下发由房间id和行动代码
	 *
	 * @param roomId      房间id
	 * @param actionCodes 操作代码
	 * @return {@code List<CrTaskLog> }
	 */
	List<CrTaskLog> selectReleaseByRoomIdAndActionCodes(String roomId, List<Integer> actionCodes);

	/**
	 * 查询下发由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTaskLog> }
	 */
	List<CrTaskLog> selectReleaseByRoomId(String roomId);

	/**
	 * 获取动态日志由房间id和用户id
	 *
	 * @param roomId      房间id
	 * @param studentId   学员id
	 * @param ids         id列表
	 * @param actionCodes 操作代码
	 * @return {@code List<DynamicData> }
	 */
	List<DynamicData> getDynamicLogByRoomIdAndUserId(String roomId, String studentId,
			List<String> ids, List<Integer> actionCodes);

	/**
	 * 获取下发id列表由房间id和用户id
	 *
	 * @param roomId    房间id
	 * @param studentId 学员id
	 * @return {@code List<String> }
	 */
	List<String> getReleaseIdsByRoomIdAndUserId(String roomId, String studentId);

	/**
	 * 批量插入
	 *
	 * @param taskLogs 任务日志
	 */
	void batchInsert(List<CrTaskLog> taskLogs);
}
