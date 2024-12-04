package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人统计详情4计数博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PersonalStatsDetail4CountBo implements Serializable {
    /**
     * 课程总计数
     *
     * @see Integer
     */
    @ApiModelProperty("课程总数")
    private Integer courseTotalCount = 0;

    /**
     * 理论课程总计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成理论课程数量")
    private Integer theoryCourseTotalCount = 0;

    /**
     * exp （英文）课程总计数
     *
     * @see Integer
     */
    @ApiModelProperty("实验课程数量")
    private Integer expCourseTotalCount = 0;

    /**
     * 后类实践总计数
     *
     * @see Integer
     */
    @ApiModelProperty("课后练习数量")
    private Integer afterClassPracticeTotalCount = 0;

    /**
     * 完成课程计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成课程数量")
    private Integer finishCoursesCount = 0;

    /**
     * 完成理论计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成理论课程数量")
    private Integer finishTheoryCount = 0;

    /**
     * 完成exp （英文）计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成实验课程数量")
    private Integer finishExpCount = 0;

    /**
     * 课后练习
     *
     * @see Integer
     */
    @ApiModelProperty("完成课后练习数量")
    private Integer afterClassPractice = 0;

}
