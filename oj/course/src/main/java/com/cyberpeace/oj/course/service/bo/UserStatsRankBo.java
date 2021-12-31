package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserStatsRankBo implements Serializable {
    @ApiModelProperty("课时学习用户排名")
    private List<String> lessonRecordUser;

    @ApiModelProperty("课时学习数量")
    private List<Integer> lessonRecordCount;

    @ApiModelProperty("完成实验排名")
    private List<String> finishReportUser;

    @ApiModelProperty("完成实验数量")
    private List<Integer> finishReportCount;

    @ApiModelProperty("完成课程练习排名")
    private List<String> finishPracticeUser;

    @ApiModelProperty("完成课程练习数量")
    private List<Integer> finishPracticeCount;
}
