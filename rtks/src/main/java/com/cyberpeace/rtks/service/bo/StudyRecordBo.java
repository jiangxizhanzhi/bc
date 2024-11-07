package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 学习记录 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("学习记录统计信息")
public class StudyRecordBo implements Serializable {

    /**
     * Y 课程
     */
    @ApiModelProperty("学习课程数量")
    private List<String> yCourses;

    /**
     * Y 节课
     */
    @ApiModelProperty("学习课时数量")
    private List<String> yLessons;

    /**
     * x 日期
     */
    @ApiModelProperty("时间分布")
    private List<String> xDates;

}
