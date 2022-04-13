package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;

@Mapper
@Repository
public interface PracticeOperationRefMapper extends BaseMapper<PracticeOperationRef> {

	void deleteByPaper(@Param("paperId") String paperId);

	List<PracticeOperationRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

	PracticeOperationRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceId);

	List<String> selectByPaper(@Param("paperId") String paper);

	List<PracticeOperationRef> selectByPaperId(@Param("paperId") String paperId);
}
