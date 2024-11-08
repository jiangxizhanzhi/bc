package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationTarget;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实践实操目标Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface PracticeOperationTargetMapper extends BaseMapper<PracticeOperationTarget> {

	/**
	 * 获取都由实操id列表
	 *
	 * @param operationIds 实操id列表
	 * @return {@code List<PracticeOperationTarget> }
	 */
	List<PracticeOperationTarget> getAllByOperationIds(@Param("operationIds") List<String> operationIds);

	/**
	 * 获取都由目标id列表
	 *
	 * @param targetIds 目标id列表
	 * @return {@code List<PracticeOperationTarget> }
	 */
	List<PracticeOperationTarget> getAllByTargetIds(@Param("targetIds") List<String> targetIds);

	/**
	 * 删除由实操id
	 *
	 * @param operationId 实操id
	 */
	void deleteByOperationId(@Param("operationId") String operationId);
}
