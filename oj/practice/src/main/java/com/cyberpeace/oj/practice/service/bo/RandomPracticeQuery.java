package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 随机练习查询
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RandomPracticeQuery extends PatternBase {

    /**
     * 习题集编号
     */
    @ApiModelProperty("习题集编号")
	private String practiceSetId;

    /**
     * 分类编号
     */
    @ApiModelProperty("分类编号")
	private List<String> categoryIds;

    /**
     * 题型 0=单选 1-多选.....
     */
    @ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

    /**
     * 习题数量
     */
    @ApiModelProperty("习题数量")
	private Integer count;

    /**
     * 已选择的习题集编号
     */
    @ApiModelProperty("已选择的习题集编号")
	private List<String> selectIds;
}
