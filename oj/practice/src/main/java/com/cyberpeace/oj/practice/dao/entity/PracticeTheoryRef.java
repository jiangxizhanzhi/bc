package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 派生于PracticeOperation, 在试卷中被引用
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeOperationRef
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("oj_practice_theory_ref")
public class PracticeTheoryRef extends PracticeOperationRef {

    /**
     * 选项是否随机
     *
     * @see Boolean
     */
    private Boolean optionRandom;

}
