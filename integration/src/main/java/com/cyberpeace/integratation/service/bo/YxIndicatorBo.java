package com.cyberpeace.integratation.service.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YxIndicatorBo {

    @ApiModelProperty("知识点/指标id")
    private String id;

    @ApiModelProperty("知识点/指标名称")
    private String name;
}
