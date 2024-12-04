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
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface PracticeOperationRefMapper extends BaseMapper<PracticeOperationRef> {

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
     * @return {@code List<PracticeOperationRef> }
     * @throws
     * @see String
     * @see List<String>
     * @see List<PracticeOperationRef>
     */
    List<PracticeOperationRef> selectByPracticeIds(@Param("paperId") String paperId,
			@Param("practiceIds") List<String> practiceIds);

    /**
     * 查询由实践id
     *
     * @param paperId    纸id
     * @param practiceId 实践id
     * @return {@code PracticeOperationRef }
     * @throws
     * @see String
     * @see String
     * @see PracticeOperationRef
     */
    PracticeOperationRef selectByPracticeId(@Param("paperId") String paperId,
			@Param("practiceId") String practiceId);

    /**
     * 查询由纸
     *
     * @param paper 纸
     * @return {@code List<String> }
     * @throws
     * @see String
     * @see List<String>
     */
    List<String> selectByPaper(@Param("paperId") String paper);

    /**
     * 查询由纸id
     *
     * @param paperId 纸id
     * @return {@code List<PracticeOperationRef> }
     * @throws
     * @see String
     * @see List<PracticeOperationRef>
     */
    List<PracticeOperationRef> selectByPaperId(@Param("paperId") String paperId);
}
