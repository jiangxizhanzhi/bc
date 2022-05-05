package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_eval_algorithm
 */
@TableName(value ="cr_eval_algorithm")
@Data
public class EvalAlgorithm extends BaseTable {
    

    @TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4
     */
    private Integer type;

    /**
     * 
     */
    private Integer algorithmLanguage;

    /**
     * 算法语言
        1：python
     */
    private String code;

    /**
     * 
     */
    private String params;

    /**
     * 
     */
    private Integer paramsNum;

    /**
     * 创建人
     */
    private String createUserId;
}