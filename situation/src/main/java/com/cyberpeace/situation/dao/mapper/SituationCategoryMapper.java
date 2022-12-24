package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationCategory;

@Mapper
public interface SituationCategoryMapper extends BaseMapper<SituationCategory> {

	SituationCategory selectByTaskType(@Param("taskType") String taskType);

}
