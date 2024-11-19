package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课堂练习Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeBo implements Serializable {

	/**
	 * 是否已发布
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否已发布")
	private Boolean released=true;

	/**
	 * 是否有习题
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有习题")
	private Boolean hasPractice=true;

	/**
	 * 练习完成统计
	 *
	 * @see ListRsp<PracticePaperEvaluateBo>
	 */
	@ApiModelProperty("练习完成统计")
	private ListRsp<PracticePaperEvaluateBo> data;
}
