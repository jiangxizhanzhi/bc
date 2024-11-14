package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;

/**
 * 练习实操裁判Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface PracticeOperationRefMapper extends BaseMapper<PracticeOperationRef> {

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
     * @return {@code List<PracticeOperationRef> }
     */
    List<PracticeOperationRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

    /**
     * 查询由练习id
     *
     * @param paperId    试卷id
     * @param practiceId 练习id
     * @return {@code PracticeOperationRef }
     */
    PracticeOperationRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceId);

    /**
     * 查询由试卷
     *
     * @param paper 试卷
     * @return {@code List<String> }
     */
    List<String> selectByPaper(@Param("paperId") String paper);

    /**
     * 查询由试卷id
     *
     * @param paperId 试卷id
     * @return {@code List<PracticeOperationRef> }
     */
    List<PracticeOperationRef> selectByPaperId(@Param("paperId") String paperId);
}
