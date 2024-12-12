package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.service.bo.UserNoteScoreBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课堂统计 bo
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
public class ClassroomStatisticBo implements Serializable {

	/**
	 * 等级分布
	 *
	 * @see GradeDistributionBo
	 */
	@ApiModelProperty("成绩统计")
	private GradeDistributionBo gradeDistribution;

	/**
	 * 标志信息
	 *
	 * @see SignInfoBo
	 */
	@ApiModelProperty("签到统计")
	private SignInfoBo signInfo;

	/**
	 * 前 20 名
	 *
	 * @see List<UserNoteScoreBo>
	 */
	@ApiModelProperty("top20")
	private List<UserNoteScoreBo> top20;

}
