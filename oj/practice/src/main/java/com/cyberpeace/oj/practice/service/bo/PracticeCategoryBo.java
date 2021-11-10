package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeCategoryBo implements Serializable {

    @ApiModelProperty("习题分类名称")
    private String name;

    @ApiModelProperty("习题分类编号")
    private String resourceId;

}
