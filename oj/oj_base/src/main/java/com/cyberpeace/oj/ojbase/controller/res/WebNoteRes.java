package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Web注意结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebNoteRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记编号")
	private String resourceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("笔记创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否通过")
	private Boolean passed;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	@ApiModelProperty("评估内容")
	private String evaluateComment;

	/**
	 * 是提交
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("实验报告是否已提交")
	private Boolean isSubmit;

}
