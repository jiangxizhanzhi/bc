package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeBo {

	@ApiModelProperty("是否已发布")
	private Boolean released=true;

	@ApiModelProperty("是否有习题")
	private Boolean hasPractice=true;

	@ApiModelProperty("练习完成统计")
	private ListRsp<PracticePaperEvaluateBo> data;
}
