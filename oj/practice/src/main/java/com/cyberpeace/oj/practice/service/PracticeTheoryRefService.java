package com.cyberpeace.oj.practice.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

/**
 * 实践理论裁判Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PracticeTheoryRefService extends IService<PracticeTheoryRef> {

    /**
     * 获取试卷基础理论习题
     *
     * @param paperId
     * @param viewAnswer
     * @return
     * @throws
     * @see String
     * @see Boolean
     * @see List<TheoryLessonBo>
     */
    List<TheoryLessonBo> selectByPaper(String paperId, Boolean viewAnswer);

    /**
     * 根据习题编号查询试卷习题
     *
     * @param paperId    纸id
     * @param practiceId
     * @return
     * @throws
     * @see String
     * @see String
     * @see PracticeTheory
     */
    PracticeTheory selectByPracticeId(String paperId, String practiceId);

    /**
     * 导出课程练习
     *
     * @param pathname 路径
     * @param paperId  纸id
     * @throws
     * @see String
     * @see String
     */
    void exportLessonPractice(String pathname, String paperId);
}
