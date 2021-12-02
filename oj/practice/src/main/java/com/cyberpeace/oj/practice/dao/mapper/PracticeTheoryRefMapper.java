package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeTheoryRefMapper extends BaseMapper<PracticeTheoryRef> {

	Integer saveBatch(@Param("practiceList") List<PracticeTheoryRef> practiceList);

	void deleteByPaper(@Param("paperId") String paperId);

	List<PracticeTheoryRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

	PracticeTheoryRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceIds);

	/**
	 * 查询课后练习数据编号
	 * @param paperId
	 * @return
	 */
    List<String> selectByPaper(@Param("paperId") String paperId);
}
