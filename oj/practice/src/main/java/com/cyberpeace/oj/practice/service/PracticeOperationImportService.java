package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;

import java.util.List;
import java.util.Map;

/**
 * 实践实操导入Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface PracticeOperationImportService {

    /**
     * 课后练习数据解析
     *
     * @param lessonPracticePath
     * @param practiceTopo
     * @return
     * @throws
     * @see String
     * @see Map<String, String>
     * @see List<PracticeOperation>
     */
    List<PracticeOperation> parseLessonOperation(String lessonPracticePath, Map<String, String> practiceTopo);

    /**
     * 解析课后练习实操题目
     *
     * @param path
     * @param envReqs
     * @param practiceSet
     * @return
     * @throws
     * @see String
     * @see Map<String, String>
     * @see PracticeSet
     * @see PracticeOperation
     */
    PracticeOperation parseLessonPracticeCtf(String path, Map<String, String> envReqs,
                                        PracticeSet practiceSet);

    /**
     * 资管实操习题数据解析
     *
     * @param path        习题资源路径
     * @param envReqs
     * @param practiceSet
     * @return
     * @throws
     * @see String
     * @see Map<String, String>
     * @see PracticeSet
     * @see PracticeOperation
     */
    PracticeOperation parseRmCtf(String path, Map<String, String> envReqs, PracticeSet practiceSet);
}
