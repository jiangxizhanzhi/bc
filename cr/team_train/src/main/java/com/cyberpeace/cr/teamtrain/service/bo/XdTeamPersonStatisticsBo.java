package com.cyberpeace.cr.teamtrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * xd项目训练人数统计Bo
 *
 * @author SN
 * @date 2025/09/15
 */
@Data
@ApiModel("xd项目训练人数统计Bo")
public class XdTeamPersonStatisticsBo {

    /**
     * 月份
     */
    @ApiModelProperty("月份")
    private String month;

    /**
     * 实验人数
     */
    @ApiModelProperty("实验人数")
    private Long syNum;

    /**
     * 科目人数
     */
    @ApiModelProperty("科目人数")
    private Long kmNum;

    /**
     * 综合演训人数
     */
    @ApiModelProperty("综合演训人数")
    private Long zhyxNum;
}
