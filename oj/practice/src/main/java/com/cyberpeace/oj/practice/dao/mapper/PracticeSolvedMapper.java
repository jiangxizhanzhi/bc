package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PracticeSolvedMapper extends BaseMapper<PracticeSolved> {

	PracticeSolved selectUserAndId(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("practiceId") String practiceId);

	List<String> selectUserAndSolved(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("solved") Boolean solved);

	int updateSolved(@Param("solved") PracticeSolved practiceSolved);
}
