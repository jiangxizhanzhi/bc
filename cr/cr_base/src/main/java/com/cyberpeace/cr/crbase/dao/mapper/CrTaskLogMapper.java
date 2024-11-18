package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.service.context.DynamicData;

/**
 * 靶场任务日志映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CrTaskLogMapper extends BaseMapper<CrTaskLog> {

	/**
	 * 查询释放由房间id和行动代码
	 *
	 * @param roomId      房间id
	 * @param actionCodes 操作代码
	 * @return {@code List<CrTaskLog> }
	 * @throws
	 * @see String
	 * @see List<Integer>
	 * @see List<CrTaskLog>
	 */
	List<CrTaskLog> selectReleaseByRoomIdAndActionCodes(String roomId, List<Integer> actionCodes);

	/**
	 * 查询释放由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTaskLog> }
	 * @throws
	 * @see String
	 * @see List<CrTaskLog>
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
	 * @throws
	 * @see String
	 * @see String
	 * @see List<String>
	 * @see List<Integer>
	 * @see List<DynamicData>
	 */
	List<DynamicData> getDynamicLogByRoomIdAndUserId(String roomId, String studentId,
			List<String> ids, List<Integer> actionCodes);

	/**
	 * 获取释放id列表由房间id和用户id
	 *
	 * @param roomId    房间id
	 * @param studentId 学员id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see String
	 * @see List<String>
	 */
	List<String> getReleaseIdsByRoomIdAndUserId(String roomId, String studentId);

	/**
	 * 批量插入
	 *
	 * @param taskLogs 任务日志
	 * @throws
	 * @see List<CrTaskLog>
	 */
	void batchInsert(List<CrTaskLog> taskLogs);
}
