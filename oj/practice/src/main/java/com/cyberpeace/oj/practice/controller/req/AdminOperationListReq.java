package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理实操列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class AdminOperationListReq extends PageReqBase {

    /**
     * 实践设置id
     */
    @ApiModelProperty(name = "practice_set_id", value = "习题集")
    private String practiceSetId;

    /**
     * 类别id
     */
    @ApiModelProperty(name = "category_id", value = "方向")
    private String categoryId;

    /**
     * 难度
     */
    @ApiModelProperty(name = "difficulty", value = "难度")
    private Integer difficulty;

    public void setPractice_set_id(String practiceSetId) {
        this.practiceSetId = practiceSetId;
    }

    public void setCategory_id(String categoryId) {
        this.categoryId = categoryId;
    }
}
