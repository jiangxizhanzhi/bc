package com.cyberpeace.frame.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticsRes implements Serializable {

    @ApiModelProperty("类型名称")
    private String name;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("本月新增")
    private Integer thisAdd;

    @ApiModelProperty("上月新增")
    private Integer lastAdd;

}
