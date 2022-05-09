package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class PracticeBriefBo implements Serializable {

	@ApiModelProperty("理论题正确率")
	private String accuracy;

	@ApiModelProperty("评分等级")
	private Integer level=null;

	@ApiModelProperty("客观题数量")
	private Integer objectCount;

	@ApiModelProperty("主观题数量")
	private Integer subjectCount;

	@ApiModelProperty("题目总数量")
	private Integer totalCount;

}
