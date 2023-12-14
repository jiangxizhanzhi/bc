package com.cyberpeace.oj.practice.controller.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PracticeLastWeekRes implements Serializable {


    @ApiModelProperty("总理论题数")
    private Date date;

    @ApiModelProperty("完成理论题数")
    private Integer finishedTheories;

    @ApiModelProperty("完成实操题数")
    private Integer practiceType;

}
