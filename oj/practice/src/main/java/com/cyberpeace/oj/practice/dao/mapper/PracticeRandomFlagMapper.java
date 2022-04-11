package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeRandomFlag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PracticeRandomFlagMapper extends BaseMapper<PracticeRandomFlag> {

	PracticeRandomFlag selectByUserAndPractice(@Param("userId") String userId,
			@Param("practiceId") String practiceId, @Param("type") Integer type);

	void deleteByPractice(@Param("practiceId") String resourceId, @Param("type") Integer code);
}
