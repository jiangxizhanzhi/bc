package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class TimeScheduleBo implements Serializable {

    @ApiModelProperty("课堂信息")
    private List<ClassroomBo> classrooms;

    @ApiModelProperty("时间")
    private String date;

}
