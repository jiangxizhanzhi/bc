package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;

import java.util.List;

public interface PracticeOperationRefService extends IService<PracticeOperationRef> {

    /**
     * 根据试卷查询查询课后练习习题数据
     * @param paperId
     * @return
     */
    List<OperationLessonBo> selectByPaperId(String paperId);

    /**
     * 根据习题编号查询
     * @param paperId
     * @param practiceId
     * @return
     */
    PracticeOperation selectByPracticeId(String paperId, String practiceId);

}
