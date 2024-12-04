package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实践基地
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PracticeBase extends BaseTable implements Serializable {

	/**
	 * 资源ID
	 *
	 * @see String
	 */
	@ApiModelProperty("资源id")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 是否内建
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否内建")
	@JsonIgnore
	private Boolean builtin;

	/**
	 * 所属分类
	 *
	 * @see String
	 */
	@ApiModelProperty("分类id")
	private String categoryId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	@TableField("public")
	private Boolean isPublic;

	/**
	 * 所属习题集
	 *
	 * @see String
	 */
	@ApiModelProperty("所属习题集id")
	private String practiceSetId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 答案 不同题型记录形式不同. 例: 单选-A, 多选-A|B, 判断-A, 填空-[
	 * {
	 * \"weight\":40,\"value\":\"A|a\"
	 * }
	 * ,
	 * {
	 * \"weight\":60,\"value\":\"B\"
	 * }
	 * ], 简答-纯文本")
	 *
	 * @see String
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 难度, 目前仅操作题使用 0-初级, 1-中级, 2-高级
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 知识点
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 分值
	 *
	 * @see Double
	 */
	@ApiModelProperty("得分")
	private Double score;

	/**
	 * 创建者
	 *
	 * @see String
	 */
	@ApiModelProperty("创建用户id")
	private String createUserId;

	/**
	 * 课程方向id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向id")
	private String courseCategoryId;

}
