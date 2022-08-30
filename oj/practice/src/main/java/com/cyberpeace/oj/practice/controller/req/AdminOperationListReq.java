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

    @ApiModelProperty(name = "dynamic_flag", value = "flag类型", allowableValues = "0, 1, 2")
    private Integer dynamicFlag;

    public void setPractice_set_id(String practiceSetId) {
        this.practiceSetId = practiceSetId;
    }

    public void setCategory_id(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setDynamic_flag(Integer dynamicFlag){
        this.dynamicFlag = dynamicFlag;
    }
}
