package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课堂练习 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeBo implements Serializable {

	/**
	 * 释放
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否已发布")
	private Boolean released=true;

	/**
	 * 有实践
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有习题")
	private Boolean hasPractice=true;

	/**
	 * 数据
	 *
	 * @see ListRsp<PracticePaperEvaluateBo>
	 */
	@ApiModelProperty("练习完成统计")
	private ListRsp<PracticePaperEvaluateBo> data;
}
