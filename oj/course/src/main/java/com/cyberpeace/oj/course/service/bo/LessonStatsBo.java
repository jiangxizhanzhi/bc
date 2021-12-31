package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LessonStatsBo implements Serializable {
    @ApiModelProperty("课时resource_id")
    private String resourceId;

    @ApiModelProperty("课程resource_id")
    private String courseId;

    @ApiModelProperty("课时名称")
    private String name;

    @ApiModelProperty("课时类型")
    private Integer type;

    @ApiModelProperty("课时难度")
    private Integer difficulty;

    @ApiModelProperty("学习人数")
    private Integer learningCount;

    @ApiModelProperty("提交报告数量")
    private Integer reportCount;

    @ApiModelProperty("完成练习数")
    private Integer finishPracticeCount;

}
