package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人统计 Detail4 课程 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PersonalStatsDetail4CourseBo implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("课程resource_id")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
    private String name;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
    private Integer difficulty;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("方向名称")
    private String directionName;

    /**
     * 图标
     *
     * @see String
     */
    @ApiModelProperty("课程图片地址")
    private String logo;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    /**
     * 博学理论
     *
     * @see Integer
     */
    @ApiModelProperty("已学习理论课时")
    private Integer learnedTheory;

    /**
     * 学到了经验
     *
     * @see Integer
     */
    @ApiModelProperty("已学习实验课时")
    private Integer learnedExp;

    /**
     * 课后练习
     *
     * @see Integer
     */
    @ApiModelProperty("已完成课后练习")
    private Integer learnedAfterClassPractice;

    /**
     * 学习 EXP 报告
     *
     * @see Integer
     */
    @ApiModelProperty("已提交实验报告")
    private Integer learnedExpReport;

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("正在学习课时")
    private String lessonId;

    /**
     * 创建时间
     *
     * @see String
     */
    @ApiModelProperty("最近一次学习时间")
    private String createTime;

    /**
     * 学习类型
     *
     * @see Integer
     */
    @ApiModelProperty("课程学习类型")
    private Integer learnedType;

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("课堂id")
    private String roomId;
}
