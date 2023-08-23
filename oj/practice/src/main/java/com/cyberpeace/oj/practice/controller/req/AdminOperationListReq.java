package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class AdminOperationListReq extends PageReqBase {

    @ApiModelProperty(name = "practice_set_id", value = "习题集")
    private String practiceSetId;

    @ApiModelProperty(name = "category_id", value = "方向")
    private String categoryId;

    @ApiModelProperty("知识点，用逗号隔开多个知识点")
    private List<String> knowledge;

    public void setPractice_set_id(String practiceSetId) {
        this.practiceSetId = practiceSetId;
    }

    public void setCategory_id(String categoryId) {
        this.categoryId = categoryId;
    }
}
