package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程管理员 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("后台课程信息")
public class CourseAdminBo extends CourseWebBo {

    /**
     * 认证
     */
    @ApiModelProperty("授权访问类型")
    private Integer auth;

    /**
     * 认证计数
     */
    @ApiModelProperty("授权访问数量")
    private Integer authCount;

    /**
     * 共享
     */
    @ApiModelProperty("授权操作类型")
    private Integer share;

    /**
     * 共享计数
     */
    @ApiModelProperty("授权操作数量")
    private Integer shareCount;
}
