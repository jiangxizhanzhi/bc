package com.cyberpeace.oj.practice.dao.entityUnused;

import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;

/**
 * auth 与 share 功能暂时保留
 */
public class PracticeSet extends BaseTable {
    private boolean builtin;

    private Integer status;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封面")
    private String logo;

    @ApiModelProperty("练习类型")
    private String practiceType;

    @ApiModelProperty("是否公开")
    private boolean isPublic;
}
