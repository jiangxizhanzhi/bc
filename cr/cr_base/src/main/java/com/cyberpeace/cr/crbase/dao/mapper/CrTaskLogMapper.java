package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.service.context.DynamicData;

/**
 * cr任务日志映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface CrTaskLogMapper extends BaseMapper<CrTaskLog> {

	/**
	 * 通过房间id和动作代码获取下发任务
	 *
	 * @param roomId      房间id
	 * @param actionCodes 行动准则
	 * @return {@link List}<{@link CrTaskLog}>
	 */
	List<CrTaskLog> selectReleaseByRoomIdAndActionCodes(String roomId, List<Integer> actionCodes);

	/**
	 * 通过房间id和用户id获取动态日志
	 *
	 * @param roomId      房间id
	 * @param studentId   学生证
	 * @param ids         id
	 * @param actionCodes 行动准则
	 * @return {@link List}<{@link DynamicData}>
	 */
	List<DynamicData> getDynamicLogByRoomIdAndUserId(String roomId, String studentId,
			List<String> ids, List<Integer> actionCodes);

	/**
	 * 通过房间id和用户id获取下发id
	 *
	 * @param roomId    房间id
	 * @param studentId 学生证
	 * @return {@link List}<{@link String}>
	 */
	List<String> getReleaseIdsByRoomIdAndUserId(String roomId, String studentId);
}
