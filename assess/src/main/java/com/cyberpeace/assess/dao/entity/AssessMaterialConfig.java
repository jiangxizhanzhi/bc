package com.cyberpeace.assess.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 
 * @TableName assess_case_config
 */
@Data
@TableName("assess_material_config")
public class AssessMaterialConfig extends BaseTable {

    /**
     * 
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 案例id
     */
    private String caseId;

    /**
     * 材料id
     */
    private String materialId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;


}