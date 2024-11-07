package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色课程 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("职业课程基本信息")
public class RoleCourseBo implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("课程编号")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("课程名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty("课程海报")
    private String logo;

    /**
     * 课计数
     */
    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    /**
     * 课程时间
     */
    @ApiModelProperty("课程时长")
    private Integer courseDuration;

    /**
     * 困难
     */
    @ApiModelProperty("课程难度")
    private Integer difficulty;

    /**
     * 面试
     */
    @ApiModelProperty("课程访问权限")
    private Boolean interview;

}
