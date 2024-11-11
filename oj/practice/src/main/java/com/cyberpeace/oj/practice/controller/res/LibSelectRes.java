package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自由查询结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class LibSelectRes implements Serializable {

    /**
     * 练习设置
     */
    @ApiModelProperty("习题集数据")
    private List<PracticeSetRes> practiceSet;

    /**
     * 类别
     */
    @ApiModelProperty("分类数据")
    private List<CategoryListBo> category;

    /**
     * 问题类型
     */
    @ApiModelProperty("题目类型 0=单选 1=多选....")
    private List<Integer> questionType;
}
