package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.service.context.DynamicData;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrTaskLogMapper extends BaseMapper<CrTaskLog> {

	List<CrTaskLog> selectReleaseByRoomIdAndActionCodes(String roomId, List<Integer> actionCodes);

	List<DynamicData> getDynamicLogByRoomIdAndUserId(String roomId, String studentId,
			List<String> ids, List<Integer> actionCodes);

	List<String> getReleaseIdsByRoomIdAndUserId(String roomId, String studentId);

	List<CrTaskLog> getByMissionIdAndUserId(String missionId);

	void batchInsert(@Param("taskLogs") List<CrTaskLog> taskLogs);
}
