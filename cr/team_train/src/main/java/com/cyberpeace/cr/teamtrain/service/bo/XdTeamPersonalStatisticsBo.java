package com.cyberpeace.cr.teamtrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 训练学员统计详情
 *
 * @author SN
 * @date 2025/09/16
 */
@Data
@ApiModel("训练学员统计详情")
public class XdTeamPersonalStatisticsBo implements Serializable {

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
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 用户组织信息
     */
    @ApiModelProperty("用户组织信息")
    private String organizationData;

    /**
     * 用户组织信息
     */
    @ApiModelProperty("用户组织信息")
    private String organizationId;

    /**
     * 用户角色
     */
    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    /**
     * 训练次数
     */
    @ApiModelProperty("训练次数")
    private Integer trainTimes;

    /**
     * 实验次数
     */
    @ApiModelProperty("实验次数")
    private Integer syTimes;

    /**
     * 科目次数
     */
    @ApiModelProperty("科目次数")
    private Integer kmTimes;

    /**
     * 综合演训次数
     */
    @ApiModelProperty("综合演训次数")
    private Integer zhyxTimes;

    /**
     * 任务数
     */
    @ApiModelProperty("任务数")
    private Integer taskNum;

    /**
     * 任务正确率
     */
    @ApiModelProperty("任务正确率")
    private Double taskAccuracy;

    /**
     * 累计得分
     */
    @ApiModelProperty("累计得分")
    private Integer totalScore;

    /**
     * 综合排名
     */
    @ApiModelProperty("综合排名")
    private Integer overallRank;

}
