package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SolvedTop5 {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("是否解出")
    private boolean solved;

    @ApiModelProperty("答题时间")
    private Date updateTime;
}
