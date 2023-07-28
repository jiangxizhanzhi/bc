package com.cyberpeace.oj.practice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeBase extends BaseTable implements Serializable {

	/** 资源ID*/
	@ApiModelProperty("资源id")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/** 是否内建*/
	@ApiModelProperty("是否内建")
	@JsonIgnore
	private Boolean builtin;

	/** 所属分类*/
	@ApiModelProperty("分类id")
	private String categoryId;

	/** 标题*/
	@ApiModelProperty("标题")
	private String title;

	/** 公开*/
	@ApiModelProperty("是否公开")
	@TableField("public")
	private Boolean isPublic;

	/** 所属习题集*/
	@ApiModelProperty("所属习题集id")
	private String practiceSetId;

	/**内容*/
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 答案 不同题型记录形式不同. 例: 单选-A, 多选-A|B, 判断-A, 填空-[{\"weight\":40,\"value\":\"A|a\"}, {\"weight\":60,\"value\":\"B\"}], 简答-纯文本")
	 */
	@ApiModelProperty("答案")
	private String answer;

	/** 难度, 目前仅操作题使用 0-初级, 1-中级, 2-高级*/
	@ApiModelProperty("难度")
	private Integer difficulty;

	/** 知识点*/
	@ApiModelProperty("知识点")
	private String knowledge;

	/** 分值*/
	@ApiModelProperty("得分")
	private Double score;

	/** 创建者*/
	@ApiModelProperty("创建用户id")
	private String createUserId;

}
