package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationCategory;

/**
 * 大屏类别映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface SituationCategoryMapper extends BaseMapper<SituationCategory> {

	/**
	 * 通过任务类型选择
	 *
	 * @param taskType 任务类型
	 * @return {@code SituationCategory}
	 */
	SituationCategory selectByTaskType(@Param("taskType") String taskType);

	/**
	 * 通过名称选择
	 *
	 * @param name 名称
	 * @return {@code SituationCategory}
	 */
	SituationCategory selectByName(@Param("name") String name);

}
