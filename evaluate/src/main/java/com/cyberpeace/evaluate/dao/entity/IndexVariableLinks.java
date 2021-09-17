package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class IndexVariableLinks extends BaseTable {

    private String indexVariableLinksId;

    /**指标项id*/
    private String indexItemsId;

    /**变量项id*/
    private String variableItemsId;

    /**评估方案id*/
    private String estimateSchemeId;

    /**指标项算法的入参参数顺序*/
    private Integer serialNumber;


}
