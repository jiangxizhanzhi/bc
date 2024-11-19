package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程统计排Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("课程学习排名")
public class CourseStatsRankBo implements Serializable {
	/**
	 * 课程学习排名
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("课程学习排名")
    private JSONObject peopleOrderingData;

	/**
	 * 课程方向学习排名
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("课程方向学习排名")
    private JSONObject directionOrderingData;

}
