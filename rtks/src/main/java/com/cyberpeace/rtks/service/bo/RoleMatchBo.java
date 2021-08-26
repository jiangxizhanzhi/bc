package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.io.Serializable;

@Data
@ApiModel("岗位匹配度数据统计计算")
public class RoleMatchBo implements Serializable {

    @ApiModelProperty("岗位课程完成进度")
    private Integer process;

    @ApiModelProperty("完成理论总课时")
    private Integer theoryCount;

    @ApiModelProperty("完成实验总课时")
    private Integer exerciseCount;

    @ApiModelProperty("课程进度信息")
    private List<CourseKnowledgeBo> courses;

}
