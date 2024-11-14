package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 派生于PracticeOperation, 在试卷中被引用
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("oj_practice_theory_ref")
public class PracticeTheoryRef extends PracticeOperationRef {

    /**
     * 选项是否随机
     */
    private Boolean optionRandom;

}
