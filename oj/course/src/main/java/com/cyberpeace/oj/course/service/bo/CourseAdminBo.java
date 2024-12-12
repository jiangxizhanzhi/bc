package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程管理员 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseWebBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("后台课程信息")
public class CourseAdminBo extends CourseWebBo {

    /**
     * 认证
     *
     * @see Integer
     */
    @ApiModelProperty("授权访问类型")
    private Integer auth;

    /**
     * 认证计数
     *
     * @see Integer
     */
    @ApiModelProperty("授权访问数量")
    private Integer authCount;

    /**
     * 共享
     *
     * @see Integer
     */
    @ApiModelProperty("授权操作类型")
    private Integer share;

    /**
     * 共享计数
     *
     * @see Integer
     */
    @ApiModelProperty("授权操作数量")
    private Integer shareCount;
}
