package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实践理论裁判Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface PracticeTheoryRefMapper extends BaseMapper<PracticeTheoryRef> {

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
	 * @return {@code List<PracticeTheoryRef> }
	 */
	List<PracticeTheoryRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

	/**
	 * 查询由实践id
	 *
	 * @param paperId     纸id
	 * @param practiceIds 实践id列表
	 * @return {@code PracticeTheoryRef }
	 */
	PracticeTheoryRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceIds);

	/**
	 * 查询课后练习数据编号
	 *
	 * @param paperId
	 * @return
	 */
	List<String> selectByPaper(@Param("paperId") String paperId);
}
