package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;

/**
 * 实践实操裁判Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface PracticeOperationRefMapper extends BaseMapper<PracticeOperationRef> {

	/**
	 * 按纸张删除
	 *
	 * @param paperId 纸id
	 */
	void deleteByPaper(@Param("paperId") String paperId);

	/**
	 * 查询由实践id列表
	 *
	 * @param paperId     纸id
	 * @param practiceIds 实践id列表
	 * @return {@code List<PracticeOperationRef> }
	 */
	List<PracticeOperationRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

	/**
	 * 查询由实践id
	 *
	 * @param paperId    纸id
	 * @param practiceId 实践id
	 * @return {@code PracticeOperationRef }
	 */
	PracticeOperationRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceId);

	/**
	 * 查询由纸
	 *
	 * @param paper 纸
	 * @return {@code List<String> }
	 */
	List<String> selectByPaper(@Param("paperId") String paper);

	/**
	 * 查询由纸id
	 *
	 * @param paperId 纸id
	 * @return {@code List<PracticeOperationRef> }
	 */
	List<PracticeOperationRef> selectByPaperId(@Param("paperId") String paperId);
}
