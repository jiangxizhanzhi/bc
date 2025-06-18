package com.cyberpeace.ai.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.ai.model.vo.practice.PracticeTheoryDataVO;

@Mapper
@Repository
public interface UserProfileMapper {

	List<PracticeTheoryDataVO> statisticTheoryByType(@Param("practiceIds") List<String> practiceIds,
			@Param("userId") String userId);
}
