package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListBo;

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

	List<EvaluationListBo> list(@Param("roomId") String roomId, @Param("roleId") String roleId,
			@Param("userId") String userId, @Param("evaluateStatus") Integer evaluateStatus);

	EvaluationMissionBo getMissionBoById(@Param("taskSubmitId") String taskSubmitId);
}
