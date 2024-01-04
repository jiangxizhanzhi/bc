package com.cyberpeace.oj.person.controller.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SystemStaticsRes implements Serializable {

    @ApiModelProperty("学员人数")
    private Integer personCount;

    @ApiModelProperty("课程数")
    private Integer courseCount;

    @ApiModelProperty("课时数")
    private Integer lessonCount;

    @ApiModelProperty("真题数")
    private Integer trueCount;

    @ApiModelProperty("真卷数")
    private Integer truePaperCount;

    @ApiModelProperty("习题总数")
    private Integer practiceCount;


}
