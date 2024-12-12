package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程统计请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class CourseStatsReq extends PageReqBase {
    /**
     * 方向
     *
     * @see String
     */
    @ApiModelProperty("方向")
    private String direction;

    /**
     * 难
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
    private Integer difficult;

}
