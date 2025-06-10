package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseClassTimeBo implements Serializable {

    /**
     * 开始时间
     *
     * @see Date
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    @ApiModelProperty("结束时间")
    private Date endTime;
}
