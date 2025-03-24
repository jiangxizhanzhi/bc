package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理实操列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class AdminOperationListReq extends PageReqBase {

    /**
     * 实践设置id
     *
     * @see String
     */
    @ApiModelProperty(name = "practice_set_id", value = "习题集")
    private String practiceSetId;

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty(name = "course_category_id", value = "方向")
    private String courseCategoryId;

    @ApiModelProperty(name = "category_id", value = "方向")
    private String categoryId;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty(name = "difficulty", value = "难度")
    private Integer difficulty;

    /**
     * 是否使用动态FLAG (拥有场景时使用)
     *
     * @see Integer
     */
    @ApiModelProperty(value = "是否使用动态FLAG,type=52", name = "dynamic_flag")
    private Integer dynamicFlag;


    public void setPractice_set_id(String practiceSetId) {
        this.practiceSetId = practiceSetId;
    }

    public void setCourse_category_id(String courseCategoryId) {
        this.courseCategoryId = courseCategoryId;
    }

    public void setCategory_id(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setDynamic_flag(Integer dynamicFlag) {
        this.dynamicFlag = dynamicFlag;
    }
}
