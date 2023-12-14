package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminLessonStatisticsDetailRes implements Serializable {

    @ApiModelProperty("课程资源编号")
    private String resourceId;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("实验评分")
    private Integer score;

    @ApiModelProperty("报告更新时间")
    private Date reportUpdateTime;

    @ApiModelProperty("评分时间")
    private Date markingTime;

    @ApiModelProperty("完成练习")
    private Integer practiceCount;

    @ApiModelProperty("组织")
    private String organizationName;

    @ApiModelProperty("是否提交报告")
    private Boolean isReport;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("答题记录")
    private String record;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String name;

}
