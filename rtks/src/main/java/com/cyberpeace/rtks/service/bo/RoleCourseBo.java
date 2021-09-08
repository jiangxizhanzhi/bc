package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("职业课程基本信息")
public class RoleCourseBo implements Serializable {

    @ApiModelProperty("课程编号")
    private String resourceId;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("课程海报")
    private String logo;

    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    @ApiModelProperty("课程时长")
    private Integer courseDuration;

    @ApiModelProperty("课程难度")
    private Integer difficulty;

}
