package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminOperationListReq extends PageReqBase {

    @ApiModelProperty(name = "practice_set_id", value = "习题集")
    private String practiceSetId;

    @ApiModelProperty(name = "category_id", value = "方向")
    private String categoryId;

    @ApiModelProperty(name = "difficulty", value = "难度")
    private Integer difficulty;

    public void setPractice_set_id(String practiceSetId) {
        this.practiceSetId = practiceSetId;
    }

    public void setCategory_id(String categoryId) {
        this.categoryId = categoryId;
    }
}
