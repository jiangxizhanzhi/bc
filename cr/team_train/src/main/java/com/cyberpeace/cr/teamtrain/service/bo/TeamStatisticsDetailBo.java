package com.cyberpeace.cr.teamtrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团队统计学详情博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("团队训练统计信息")
public class TeamStatisticsDetailBo implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 名称
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 组织数据
     */
    @ApiModelProperty("用户组织信息")
    private String organizationData;

    /**
     * 组织id
     */
    @ApiModelProperty("用户组织信息")
    private String organizationId;

    /**
     * 角色
     */
    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    /**
     * 训练次
     */
    @ApiModelProperty("训练次数")
    private Integer trainTimes;

    /**
     * 任务数量
     */
    @ApiModelProperty("任务数")
    private Integer taskNum;

    /**
     * 任务准确性
     */
    @ApiModelProperty("任务正确率")
    private Double taskAccuracy;

    /**
     * 总分
     */
    @ApiModelProperty("累计得分")
    private Integer totalScore;

    /**
     * 总体排名
     */
    @ApiModelProperty("综合排名")
    private Integer overallRank;
}
