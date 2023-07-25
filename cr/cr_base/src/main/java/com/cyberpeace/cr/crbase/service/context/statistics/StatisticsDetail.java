package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练统计信息")
public class StatisticsDetail implements Serializable {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户组织信息")
    private String organizationData;

    @ApiModelProperty("用户组织id")
    private String organizationId;

    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    @ApiModelProperty("训练次数")
    private Integer trainTimes;

    @ApiModelProperty("任务数")
    private Integer taskNum;

    @ApiModelProperty("任务正确率")
    private Double taskAccuracy;

    @ApiModelProperty("累计得分")
    private Integer totalScore;

    @ApiModelProperty("综合排名")
    private Integer overallRank;
}
