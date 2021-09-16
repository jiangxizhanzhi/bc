package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 派生于PracticeOperation, 在试卷中被引用
 */
@Data
@TableName("practice_operation_ref")
public class PracticeOperationRef extends PracticeOperation {

    /**
     * 引用原数据resourceID
     */
    private String originId;

    /**
     * 所属试卷
     */
    private String testPaper;
}
