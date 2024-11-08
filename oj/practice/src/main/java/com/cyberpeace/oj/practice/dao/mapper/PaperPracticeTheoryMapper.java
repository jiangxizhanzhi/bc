package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PracticeSolvedRes;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 纸实践理论Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface PaperPracticeTheoryMapper extends BaseMapper<PaperPracticeTheory> {

	/**
	 * 查询解决由纸id
	 *
	 * @param paperId 纸id
	 * @return {@code List<PracticeSolvedRes> }
	 */
	List<PracticeSolvedRes> selectSolvedByPaperId(@Param("paperId") String paperId);

	/**
	 * 查询实践裁判id列表由纸id
	 *
	 * @param paperId 纸id
	 * @return {@code List<String> }
	 */
	List<String> selectPracticeRefIdsByPaperId(@Param("paperId") String paperId);
}
