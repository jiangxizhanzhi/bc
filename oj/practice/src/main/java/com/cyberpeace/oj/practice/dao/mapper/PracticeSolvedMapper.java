package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PracticeSolvedMapper extends BaseMapper<PracticeSolved> {

	PracticeSolved selectUserAndId(@Param("userId") String userId,
			@Param("practiceId") String practiceId);
}
