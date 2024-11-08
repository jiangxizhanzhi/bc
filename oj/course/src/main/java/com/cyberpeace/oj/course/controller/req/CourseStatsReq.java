package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程统计请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class CourseStatsReq extends PageReqBase {
    /**
     * 方向
     */
    @ApiModelProperty("方向")
    private String direction;

    /**
     * 难
     */
    @ApiModelProperty("难度")
    private Integer difficult;

}
