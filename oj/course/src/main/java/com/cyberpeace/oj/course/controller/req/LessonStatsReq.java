package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 课统计请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class LessonStatsReq extends PageReqBase {
    /**
     * 课程id
     */
    @NotNull
    @ApiModelProperty("课程id")
    private String course_id;

    /**
     * 类型
     */
    @ApiModelProperty("课时类型")
    private Integer type;

}
