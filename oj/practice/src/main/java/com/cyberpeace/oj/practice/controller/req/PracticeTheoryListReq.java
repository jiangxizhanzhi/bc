package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PracticeTheoryListReq extends PageReqBase {

    @ApiModelProperty(value = "难度", allowableValues = "0, 1, 2")
    private Integer difficulty;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("习题集")
    private String practiceSet;

    @ApiModelProperty("题型")
    private Integer questionType;

}
