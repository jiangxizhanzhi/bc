package com.cyberpeace.cr.redvsblue.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * RB统计学详情博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("红蓝对抗统计信息")
public class RbStatisticsDetailBo implements Serializable {

    /**
     * 用户id
     *
     * @see String
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 用户名
     *
     * @see String
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 组织数据
     *
     * @see String
     */
    @ApiModelProperty("用户组织信息")
    private String organizationData;

    /**
     * 组织id
     *
     * @see String
     */
    @ApiModelProperty("用户组织id")
    private String organizationId;

    /**
     * 角色
     *
     * @see List<JSONObject>
     */
    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    /**
     * 训练次
     *
     * @see Integer
     */
    @ApiModelProperty("训练次数")
    private Integer trainTimes;

    /**
     * 任务数量
     *
     * @see Integer
     */
    @ApiModelProperty("任务数")
    private Integer taskNum;

    /**
     * 任务准确性
     *
     * @see Double
     */
    @ApiModelProperty("任务正确率")
    private Double taskAccuracy;

    /**
     * 总分
     *
     * @see Integer
     */
    @ApiModelProperty("累计得分")
    private Integer totalScore;

    /**
     * 总体排名
     *
     * @see Integer
     */
    @ApiModelProperty("综合排名")
    private Integer overallRank;
}
