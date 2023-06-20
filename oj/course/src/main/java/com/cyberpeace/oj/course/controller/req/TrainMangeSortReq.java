package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class TrainMangeSortReq implements Serializable {


    @ApiModelProperty("父resourceId")
    @NotBlank
    private String parent;

    @ApiModelProperty("框架resourceId")
    @NotBlank
    private List<String> resourceIdList;

}
