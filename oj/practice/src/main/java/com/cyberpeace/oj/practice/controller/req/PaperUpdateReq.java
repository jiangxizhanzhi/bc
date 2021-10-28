package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PaperUpdateReq extends PaperAddReq {

    @ApiModelProperty("试卷编号")
    private String resourceId;

}
