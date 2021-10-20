package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class BatchDeleteReq implements Serializable {

    @ApiModelProperty("资源编号数组")
    @NotEmpty
    private List<String> resourceIds;

}
