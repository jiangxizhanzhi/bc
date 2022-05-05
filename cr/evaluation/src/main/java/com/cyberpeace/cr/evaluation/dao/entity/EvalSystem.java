package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_eval_system
 */
@TableName(value ="cr_eval_system")
@Data
public class EvalSystem extends BaseTable {
    

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
     * 连接线
     */
    private String  connlist;
    /**
     * 创建人
     */
    private String createUserId;
}