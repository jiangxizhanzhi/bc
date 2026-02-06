package com.cyberpeace.cr.teamtrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * xd项目训练场次统计Bo
 *
 * @author SN
 * @date 2025/09/15
 */
@Data
@ApiModel("xd项目训练场次统计Bo")
public class XdTeamTimesStatisticsBo {

    /**
     * 月份
     */
    @ApiModelProperty("月份")
    private String month;

    /**
     * 实验场次
     */
    @ApiModelProperty("实验场次")
    private Long syNum;

    /**
     * 科目场次
     */
    @ApiModelProperty("科目场次")
    private Long kmNum;

    /**
     * 综合演训场次
     */
    @ApiModelProperty("综合演训场次")
    private Long zhyxNum;
}
