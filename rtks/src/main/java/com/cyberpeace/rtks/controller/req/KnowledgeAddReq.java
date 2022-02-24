package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.res.RelatedCourse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("")
public class KnowledgeAddReq implements Serializable {

	@ApiModelProperty()
	private String resourceId;

	@NotBlank
	private String content;

	@NotBlank
	private String categoryId;

	private Integer weight = 1;

	@ApiModelProperty
	private List<RelatedCourse> courseList;

	public void validate() {
		if (null == weight || weight < CommonConstant.ZERO || weight > CommonConstant.NINE) {
			throw new BusinessException(RtksBusinessErrorCode.INVALID_WEIGHT, CommonConstant.ZERO, CommonConstant.NINE);
		}
	}
}
