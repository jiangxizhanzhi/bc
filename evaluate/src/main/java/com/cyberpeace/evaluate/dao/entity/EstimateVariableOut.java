package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class EstimateVariableOut extends BaseTable {

    String estimateVariableOutId;

    /**场景id*/
    String sceneId;

    /**主机id*/
    String hostId;

    /**变量项id*/
    String estimateVariableId;

    /**评估实例id*/
    String estimateInstanceId;

    /**得分（0-100的分数）*/
    Integer score;

}
