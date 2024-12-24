package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplateLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplateLog
 */
@Mapper
@Repository
public interface EvalMissionTemplateLogMapper extends BaseMapper<EvalMissionTemplateLog> {

//	List<EvalMissionLogRsp> getAllByTask(String resourceId);
//
//	List<EvalNodeLog> selectByIds(List<String> ids);

	void updateMissionResultAndMissionIdAndTaskId(String missionId, String missionResult, String taskId);
}




