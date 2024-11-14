package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 管理实操列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminOperationListReq extends PageReqBase {

    /**
     * 习题集
     */
    @ApiModelProperty(name = "practice_set_id", value = "习题集")
    private String practiceSetId;

    /**
     * 方向
     */
    @ApiModelProperty(name = "category_id", value = "方向")
    private String categoryId;

    /**
     * 知识点，用逗号隔开多个知识点
     */
    @ApiModelProperty("知识点，用逗号隔开多个知识点")
    private List<String> knowledge;

    public void setPractice_set_id(String practiceSetId) {
        this.practiceSetId = practiceSetId;
    }

    public void setCategory_id(String categoryId) {
        this.categoryId = categoryId;
    }
}
