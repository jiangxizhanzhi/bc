package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 自由查询结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LibSelectRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 实践设置
     *
     * @see List<PracticeSetRes>
     */
    @ApiModelProperty("习题集数据")
    private List<PracticeSetRes> practiceSet;

    /**
     * 类别
     *
     * @see List<CategoryListBo>
     */
    @ApiModelProperty("分类数据")
    private List<CategoryListBo> category;

    /**
     * 问题类型
     *
     * @see List<Integer>
     */
    @ApiModelProperty("题目类型 0=单选 1=多选....")
    private List<Integer> questionType;
}
