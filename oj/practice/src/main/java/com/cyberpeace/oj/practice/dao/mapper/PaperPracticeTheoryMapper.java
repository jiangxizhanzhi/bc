package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PracticeSolvedRes;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaperPracticeTheoryMapper extends BaseMapper<PaperPracticeTheory> {

	List<PracticeSolvedRes> selectSolvedByPaperId(@Param("paperId") String paperId);

	List<String> selectPracticeRefIdsByPaperId(@Param("paperId") String paperId);
}
