package com.cyberpeace.cr.teamtrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * xd项目得分统计Bo
 *
 * @author SN
 * @date 2025/09/15
 */
@Data
@ApiModel("xd项目得分统计Bo")
public class XdTeamPersonScoreStatisticsBo {

    /**
     * 用户resource_id
     */
    @ApiModelProperty("用户resource_id")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 累计得分
     */
    @ApiModelProperty("累计得分")
    private Double totalScore;
}
