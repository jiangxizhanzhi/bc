package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程统计 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class LessonStatsBo implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("课时resource_id")
    private String resourceId;

    /**
     * 课程id
     */
    @ApiModelProperty("课程resource_id")
    private String courseId;

    /**
     * 名称
     */
    @ApiModelProperty("课时名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty("课时类型")
    private Integer type;

    /**
     * 困难
     */
    @ApiModelProperty("课时难度")
    private Integer difficulty;

    /**
     * 学习计数
     */
    @ApiModelProperty("学习人数")
    private Integer learningCount;

    /**
     * 报告计数
     */
    @ApiModelProperty("提交报告数量")
    private Integer reportCount;

    /**
     * 完成实践计数
     */
    @ApiModelProperty("完成练习数")
    private Integer finishPracticeCount;

}
