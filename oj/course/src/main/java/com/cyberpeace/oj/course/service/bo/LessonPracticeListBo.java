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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonPracticeListBo implements Serializable {

	/**
	 * 习题总数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题总数")
	private Integer count;

	/**
	 * 总分
	 *
	 * @see Double
	 */
	@ApiModelProperty("总分")
	private Double totalScore;

	/**
	 * 练习BOS
	 *
	 * @see List<? extends TheoryLessonBo>
	 */
	private List<? extends TheoryLessonBo> practiceBos;

}
