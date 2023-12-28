package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WaterMarkProgressRes {

    @ApiModelProperty("进度")
    private Integer progress;

//    @ApiModelProperty("是否结束  true结束 false未结束")
//    private Boolean finished;
}
