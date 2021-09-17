package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估指标项表
 */
@Data
public class EstimateIndex extends BaseTable {

    /** 评估指标项id*/
    @ApiModelProperty("评估指标项id")
    private String estimateIndexId;

    /** 名称*/
    @ApiModelProperty("名称")
    private String name;

    /** 指标项算法id*/
    @ApiModelProperty("指标项算法id")
    private String algorithmId;

    /** 评估方案id*/
    @ApiModelProperty("评估方案id")
    private String estimateSchemeId;

    /**自定义参数*/
    @ApiModelProperty("自定义参数")
    private String params;

}
