package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_eval_model
 */
@TableName(value ="cr_eval_model")
@Data
public class EvalModel extends BaseTable {
    

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
     * 关联三级指标id
     */
    private String thirdIndexId;

    /**
     * 关联聚合项id
     */
    private String aggregationIds;

    /**
     * 关联变量项id
     */
    private String collectionIds;


    /**
     * 创建人
     */
    private String createUserId;
}