package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebNoteRes implements Serializable {

	@ApiModelProperty("笔记编号")
	private String resourceId;

	@ApiModelProperty("笔记内容")
	private String content;

	@ApiModelProperty("笔记创建时间")
	private Date createTime;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("分值")
	private Double score;

	@ApiModelProperty("是否通过")
	private Boolean passed;

	@ApiModelProperty("评估内容")
	private String evaluateComment;

}
