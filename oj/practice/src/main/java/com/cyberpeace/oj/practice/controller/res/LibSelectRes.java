package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class LibSelectRes implements Serializable {

    @ApiModelProperty("习题集数据")
    private List<PracticeSetRes> practiceSet;

    @ApiModelProperty("分类数据")
    private List<CategoryListBo> category;

    @ApiModelProperty("题目类型 0=单选 1=多选....")
    private List<Integer> questionType;
}
