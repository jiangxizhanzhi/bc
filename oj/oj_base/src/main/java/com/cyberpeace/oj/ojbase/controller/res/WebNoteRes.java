package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Web笔记结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebNoteRes implements Serializable {

	/**
	 * 笔记编号
	 */
	@ApiModelProperty("笔记编号")
	private String resourceId;

	/**
	 * 笔记内容
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 笔记创建时间
	 */
	@ApiModelProperty("笔记创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 分值
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 是否通过
	 */
	@ApiModelProperty("是否通过")
	private Boolean passed;

	/**
	 * 评估内容
	 */
	@ApiModelProperty("评估内容")
	private String evaluateComment;

	/**
	 * 实验报告是否已提交
	 */
	@ApiModelProperty("实验报告是否已提交")
	private Boolean isSubmit;

}
