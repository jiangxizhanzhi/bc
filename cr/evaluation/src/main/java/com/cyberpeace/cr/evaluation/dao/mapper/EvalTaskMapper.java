package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

@Mapper
@Repository
public interface EvalTaskMapper extends BaseMapper<EvalTask> {

	List<String> selectResourceIdList(String templateId);

	List<EvalTask> selectByTemplateIdAndStatus(String templateId, Integer status);

	EvalTask selectByIdAndStatus(String taskId, Integer status);

	String getTemplateIdByTaskId(String taskId);

	Boolean isNotFinished(String taskId);

	/**
	 * 校验任务名称
	 */
	Boolean existByNameAndTemplateId(@Param("taskName") String taskName,
			@Param("templateId") String templateId, @Param("taskId") String taskId);

}
