package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

/**
 * 评估任务Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface EvalTaskMapper extends BaseMapper<EvalTask> {

	/**
	 * 查询苗条任务列表由模板id和状态
	 *
	 * @param templateId 模板id
	 * @param status     状态
	 * @return {@code List<EvalTaskSlimContext> }
	 */
	List<EvalTaskSlimContext> selectSlimTaskListByTemplateIdAndStatus(String templateId,
			Integer status);

	/**
	 * 查询id列表由模板id和状态
	 *
	 * @param templateId 模板id
	 * @param status     状态
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByTemplateIdAndStatus(String templateId, Integer status);

	/**
	 * 查询由id和状态
	 *
	 * @param taskId 任务id
	 * @param status 状态
	 * @return {@code EvalTask }
	 */
	EvalTask selectByIdAndStatus(String taskId, Integer status);

	/**
	 * 获取模板id由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code String }
	 */
	String getTemplateIdByTaskId(String taskId);

	/**
	 * 未完成
	 *
	 * @param taskId 任务id
	 * @return {@code Boolean }
	 */
	Boolean isNotFinished(String taskId);

	/**
	 * 校验任务名称
	 *
	 * @param taskName   任务名称
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code Boolean }
	 */
	Boolean existByNameAndTemplateId(@Param("taskName") String taskName,
			@Param("templateId") String templateId, @Param("taskId") String taskId);

}
