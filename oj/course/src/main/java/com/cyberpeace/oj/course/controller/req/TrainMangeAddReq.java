package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class TrainMangeAddReq implements Serializable {


    @ApiModelProperty("培训框架名称")
    @NotBlank
    private String name;

    @ApiModelProperty("父级resourceId")
    private String parent;

}
