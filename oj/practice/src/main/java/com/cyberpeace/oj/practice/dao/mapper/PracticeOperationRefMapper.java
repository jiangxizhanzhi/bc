package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeOperationRefMapper extends BaseMapper<PracticeOperationRef> {
    List<TestPaperPractice> getListByTestPaper(@Param("test_paper") String testPaper);

    Integer saveBatch(@Param("practice_list") List<PracticeOperationRef> practiceList);
}
