package com.cyberpeace.oj.practice.service.simpleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PracticeCategorySimple {
    @ApiModelProperty("标识")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
