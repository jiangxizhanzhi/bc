package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("收藏请求")
public class CollectionReq implements Serializable {

    @ApiModelProperty("收藏的资源编号 课程、课时")
    @NotBlank
    private String resource;

    @ApiModelProperty("是否进行收藏 true进行收藏，false取消收藏")
    @NotNull
    private Boolean isCollection;

}
