package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.service.bo.UserNoteScoreBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课堂统计Bo
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
public class ClassroomStatisticBo implements Serializable {

	/**
	 * 成绩统计
	 *
	 * @see GradeDistributionBo
	 */
	@ApiModelProperty("成绩统计")
	private GradeDistributionBo gradeDistribution;

	/**
	 * 签到统计
	 *
	 * @see SignInfoBo
	 */
	@ApiModelProperty("签到统计")
	private SignInfoBo signInfo;

	/**
	 * top20
	 *
	 * @see List<UserNoteScoreBo>
	 */
	@ApiModelProperty("top20")
	private List<UserNoteScoreBo> top20;

}
