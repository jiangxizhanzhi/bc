package com.cyberpeace.oj.practice.service.externalbo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 外部理论博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ExternalTheoryBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
	private String resourceId;

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty("分类编号")
	private String categoryId;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty("名称")
	private String content;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
	private Integer difficulty;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人编号")
	private String createUserId;

    /**
     * 创造用户名称
     *
     * @see String
     */
    @ApiModelProperty("创建人姓名")
	private String createUserName;

    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    @ApiModelProperty("更新时间")
	private Date updateTime;

    /**
     * 理论类型
     *
     * @see Integer
     */
    @ApiModelProperty("理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单")
	private Integer TheoryType;

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty("答案")
	private String answer;

    /**
     * 空白
     *
     * @see String
     */
    @ApiModelProperty(value = "填空题答案")
	private String blank;

    /**
     * 知识
     *
     * @see String
     */
    @ApiModelProperty("知识点")
	private String knowledge;

    /**
     * 选择
     *
     * @see String
     */
    @ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

    /**
     * 关键词
     *
     * @see String
     */
    @ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

    /**
     * 课程类别id
     *
     * @see String
     */
    @ApiModelProperty("岗位方向")
    private String courseCategoryId;

}
