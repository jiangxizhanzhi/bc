package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainPlanMapper extends BaseMapper<TrainPlan> {

	TrainPlan selectByName(@Param("planName") String planName, @Param("userId") String userId);

	List<TrainPlan> selectByUser(@Param("userId") String userId);
}
