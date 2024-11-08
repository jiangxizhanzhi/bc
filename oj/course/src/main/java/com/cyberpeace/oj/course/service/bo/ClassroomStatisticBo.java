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
 * 课堂统计 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomStatisticBo implements Serializable {

	/**
	 * 等级分布
	 */
	@ApiModelProperty("成绩统计")
	private GradeDistributionBo gradeDistribution;

	/**
	 * 标志信息
	 */
	@ApiModelProperty("签到统计")
	private SignInfoBo signInfo;

	/**
	 * 前 20 名
	 */
	@ApiModelProperty("top20")
	private List<UserNoteScoreBo> top20;

}
