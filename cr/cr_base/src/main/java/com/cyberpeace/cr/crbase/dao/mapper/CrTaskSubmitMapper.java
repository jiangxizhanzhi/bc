package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.MissionTaskSummitContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrTaskSubmitMapper extends BaseMapper<CrTaskSubmit> {

	List<CrTaskSubmit> selectByRoomId(String roomId);

	CrTaskSubmit selectLastByTaskId(String taskId);

	IPage<MissionEvaluationContext> listCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

	IPage<MissionEvaluationContext> listRbCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

	List<CrTaskSubmit> selectOrderedSubmitsByTask(String resourceId);

	List<CrTaskSubmit> selectByRoomIdAndUsers(String roomId, List<String> userIds);

	List<MissionTaskSummitContext> listFinishMissionTaskSubmit(@Param("roomId") String roomId);
}
