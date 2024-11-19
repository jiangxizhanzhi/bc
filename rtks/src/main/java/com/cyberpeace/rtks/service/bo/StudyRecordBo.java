package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 研究记录Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("学习记录统计信息")
public class StudyRecordBo implements Serializable {

	/**
	 * 学习课程数量
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("学习课程数量")
    private List<String> yCourses;

	/**
	 * 学习课时数量
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("学习课时数量")
    private List<String> yLessons;

	/**
	 * 时间分布
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("时间分布")
    private List<String> xDates;

}
