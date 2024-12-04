package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理课统计学详情结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AdminLessonStatisticsDetailRes implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("课程资源编号")
    private String resourceId;

    /**
     * id
     *
     * @see String
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("实验评分")
    private Integer score;

    /**
     * 报告更新时间
     *
     * @see Date
     */
    @ApiModelProperty("报告更新时间")
    private Date reportUpdateTime;

    /**
     * 标记时间
     *
     * @see Date
     */
    @ApiModelProperty("评分时间")
    private Date markingTime;

    /**
     * 实践计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成练习")
    private Integer practiceCount;

    /**
     * 组织名称
     *
     * @see String
     */
    @ApiModelProperty("组织")
    private String organizationName;

    /**
     * 是举报
     *
     * @see Boolean
     */
    @ApiModelProperty("是否提交报告")
    private Boolean isReport;

    /**
     * 创建用户
     *
     * @see String
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 记录
     *
     * @see String
     */
    @ApiModelProperty("答题记录")
    private String record;

    /**
     * 用户名称
     *
     * @see String
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("姓名")
    private String name;

}
