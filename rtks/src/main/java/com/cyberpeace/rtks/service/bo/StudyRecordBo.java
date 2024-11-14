package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("学习记录统计信息")
public class StudyRecordBo implements Serializable {

	/**
	 * 学习课程数量
	 */
    @ApiModelProperty("学习课程数量")
    private List<String> yCourses;

	/**
	 * 学习课时数量
	 */
    @ApiModelProperty("学习课时数量")
    private List<String> yLessons;

	/**
	 * 时间分布
	 */
    @ApiModelProperty("时间分布")
    private List<String> xDates;

}
