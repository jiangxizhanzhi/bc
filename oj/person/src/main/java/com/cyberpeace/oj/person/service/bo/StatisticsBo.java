package com.cyberpeace.oj.person.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticsBo implements Serializable {

    @ApiModelProperty("总计")
    private Integer total;

    @ApiModelProperty("本月新增")
    private Integer thisAdd;

    @ApiModelProperty("上月新增")
    private Integer lastAdd;
}
