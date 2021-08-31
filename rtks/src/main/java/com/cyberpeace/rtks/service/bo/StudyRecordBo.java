package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("学习记录统计信息")
public class StudyRecordBo implements Serializable {

    @ApiModelProperty("学习课程数量")
    private List<String> yCourses;

    @ApiModelProperty("学习课时数量")
    private List<String> yLessons;

    @ApiModelProperty("时间分布")
    private List<String> xDates;

}
