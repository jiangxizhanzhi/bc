package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("进度数据")
public class AdminLessonStatisticsDetailRes implements Serializable {

    @ApiModelProperty("课程资源编号")
    private String resourceId;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("实验评分")
    private Integer score;

    @ApiModelProperty("练习数")
    private Integer practiceCount;

    @ApiModelProperty("学习人数")
    private Integer heat;

    @ApiModelProperty("实验评分")
    private Integer finishReportCount;

    @ApiModelProperty("报告更新时间")
    private Date reportUpdateTime;

    @ApiModelProperty("评分时间")
    private Date markingTime;

    @ApiModelProperty("完成练习")
    private Integer lessonCount;

    @ApiModelProperty("组织")
    private String organizationName;

    @ApiModelProperty("是否提交报告")
    private Boolean isReport;

    @ApiModelProperty("姓名")
    private String createUser;

}
