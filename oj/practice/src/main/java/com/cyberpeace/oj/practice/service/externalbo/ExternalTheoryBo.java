package com.cyberpeace.oj.practice.service.externalbo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 外部理论博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ExternalTheoryBo implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("资源编号")
	private String resourceId;

    /**
     * 类别id
     */
    @ApiModelProperty("分类编号")
	private String categoryId;

    /**
     * 内容
     */
    @ApiModelProperty("名称")
	private String content;

    /**
     * 困难
     */
    @ApiModelProperty("难度")
	private Integer difficulty;

    /**
     * 创造用户id
     */
    @ApiModelProperty("创建人编号")
	private String createUserId;

    /**
     * 创造用户名称
     */
    @ApiModelProperty("创建人姓名")
	private String createUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
	private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
	private Date updateTime;

    /**
     * 理论类型
     */
    @ApiModelProperty("理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单")
	private Integer TheoryType;

    /**
     * 答案
     */
    @ApiModelProperty("答案")
	private String answer;

    /**
     * 空白
     */
    @ApiModelProperty(value = "填空题答案")
	private String blank;

    /**
     * 知识
     */
    @ApiModelProperty("知识点")
	private String knowledge;

    /**
     * 选择
     */
    @ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

    /**
     * 关键词
     */
    @ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

}
