package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课时练习列表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class LessonPracticeListBo implements Serializable {

	/**
	 * 习题总数
	 */
	@ApiModelProperty("习题总数")
	private Integer count;

	/**
	 * 总分
	 */
	@ApiModelProperty("总分")
	private Double totalScore;

	/**
	 * 练习BOS
	 */
	private List<? extends TheoryLessonBo> practiceBos;

}
