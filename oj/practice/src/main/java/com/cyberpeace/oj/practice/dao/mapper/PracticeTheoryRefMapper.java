package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 练习理论裁判Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface PracticeTheoryRefMapper extends BaseMapper<PracticeTheoryRef> {

	/**
	 * 删除由试卷
	 *
	 * @param paperId 试卷id
	 */
	void deleteByPaper(@Param("paperId") String paperId);

	/**
	 * 查询由练习id列表
	 *
	 * @param paperId     试卷id
	 * @param practiceIds 练习id列表
	 * @return {@code List<PracticeTheoryRef> }
	 */
	List<PracticeTheoryRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

	/**
	 * 查询由练习id
	 *
	 * @param paperId     试卷id
	 * @param practiceIds 练习id列表
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
