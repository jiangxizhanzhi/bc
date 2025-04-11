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
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface PracticeTheoryRefMapper extends BaseMapper<PracticeTheoryRef> {

	/**
	 * 按纸张删除
	 *
	 * @param paperId 纸id
	 * @throws
	 * @see String
	 */
	void deleteByPaper(@Param("paperId") String paperId);

	/**
	 * 查询由实践id列表
	 *
	 * @param paperId     纸id
	 * @param practiceIds 实践id列表
	 * @return {@code List<PracticeTheoryRef> }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<PracticeTheoryRef>
	 */
	List<PracticeTheoryRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

	/**
	 * 查询由实践id
	 *
	 * @param paperId     纸id
	 * @param practiceIds 实践id列表
	 * @return {@code PracticeTheoryRef }
	 * @throws
	 * @see String
	 * @see String
	 * @see PracticeTheoryRef
	 */
	PracticeTheoryRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceIds);

	/**
	 * 查询课后练习数据编号
	 *
	 * @param paperId
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> selectByPaper(@Param("paperId") String paperId);

	List<PracticeTheoryRef> selectRefByPaper(@Param("paperId") String paperId);
}
