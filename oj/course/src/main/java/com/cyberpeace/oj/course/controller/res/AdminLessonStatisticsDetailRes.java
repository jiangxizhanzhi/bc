package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理课统计学详情结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class AdminLessonStatisticsDetailRes implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("课程资源编号")
    private String resourceId;

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 得分
     */
    @ApiModelProperty("实验评分")
    private Integer score;

    /**
     * 报告更新时间
     */
    @ApiModelProperty("报告更新时间")
    private Date reportUpdateTime;

    /**
     * 标记时间
     */
    @ApiModelProperty("评分时间")
    private Date markingTime;

    /**
     * 练习计数
     */
    @ApiModelProperty("完成练习")
    private Integer practiceCount;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织")
    private String organizationName;

    /**
     * 是举报
     */
    @ApiModelProperty("是否提交报告")
    private Boolean isReport;

    /**
     * 创建用户
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 记录
     */
    @ApiModelProperty("答题记录")
    private String record;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 名称
     */
    @ApiModelProperty("姓名")
    private String name;

}
