package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PracticeSolvedRes;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 试卷练习理论Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface PaperPracticeTheoryMapper extends BaseMapper<PaperPracticeTheory> {

	/**
	 * 查询解决由试卷id
	 *
	 * @param paperId 试卷id
	 * @return {@code List<PracticeSolvedRes> }
	 */
	List<PracticeSolvedRes> selectSolvedByPaperId(@Param("paperId") String paperId);

	/**
	 * 查询练习裁判id列表由试卷id
	 *
	 * @param paperId 试卷id
	 * @return {@code List<String> }
	 */
	List<String> selectPracticeRefIdsByPaperId(@Param("paperId") String paperId);
}
