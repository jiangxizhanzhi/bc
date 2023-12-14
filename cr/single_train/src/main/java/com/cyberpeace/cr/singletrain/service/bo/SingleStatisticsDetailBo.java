package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("单兵训练统计信息")
public class SingleStatisticsDetailBo implements Serializable {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
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
