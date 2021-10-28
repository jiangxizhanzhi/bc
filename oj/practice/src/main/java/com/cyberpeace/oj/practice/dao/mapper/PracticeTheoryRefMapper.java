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

	List<TestPaperPractice> getListByTestPaper(@Param("paperId") String paperId);

	Integer saveBatch(@Param("practiceList") List<PracticeTheoryRef> practiceList);

	void deleteByPaper(@Param("paperId") String paperId);
}
