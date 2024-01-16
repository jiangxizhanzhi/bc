package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户学习统计信息")
public class PersonStatsBo implements Serializable {

    @ApiModelProperty("用户resource_id")
    private String resourceId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("课程学习进度")
    private Integer courseProgress = 0;

    @ApiModelProperty("理论题正确率")
    private Integer theoryCorrectRate = 0;

    @ApiModelProperty("实操题正确率")
    private Integer operateCorrectRate = 0;

    @ApiModelProperty("考试通过率")
    private Integer examPassRate = 0;


}
