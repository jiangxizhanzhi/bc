package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;

/**
 * 评估任务步Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface EvalTaskStepMapper extends BaseMapper<EvalTaskStep> {

	/**
	 * 查询步id列表由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> selectStepIdsByTaskId(@Param("taskId") String taskId);

	/**
	 * 查询步骤由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code List<EvalTaskStep> }
	 * @throws
	 * @see String
	 * @see List<EvalTaskStep>
	 */
	List<EvalTaskStep> selectStepsByTaskId(@Param("taskId") String taskId);

	/**
	 * 未完成
	 *
	 * @param taskId 任务id
	 * @param stage  阶段
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see Boolean
	 */
	Boolean isNotFinished(String taskId, Integer stage);

}
