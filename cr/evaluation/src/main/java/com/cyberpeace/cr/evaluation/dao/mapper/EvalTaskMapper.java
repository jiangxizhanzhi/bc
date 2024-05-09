package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalTaskMapper extends BaseMapper<EvalTask> {

	List<EvalTaskSlimContext> selectSlimTaskListByTemplateIdAndStatus(String templateId,
			Integer status);

	List<String> selectIdsByTemplateIdAndStatus(String templateId, Integer status);

	EvalTask selectByIdAndStatus(String taskId, Integer status);

	String getTemplateIdByTaskId(String taskId);

	Boolean isNotFinished(String taskId);

	/**
	 * 校验任务名称
	 */
	Boolean existByNameAndTemplateId(@Param("taskName") String taskName,
			@Param("templateId") String templateId, @Param("taskId") String taskId);

}
