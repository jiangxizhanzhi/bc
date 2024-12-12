package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程统计 排名 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("课程学习排名")
public class CourseStatsRankBo implements Serializable {
    /**
     * 订购数据人员
     *
     * @see JSONObject
     */
    @ApiModelProperty("课程学习排名")
    private JSONObject peopleOrderingData;

    /**
     * 方向订购数据
     *
     * @see JSONObject
     */
    @ApiModelProperty("课程方向学习排名")
    private JSONObject directionOrderingData;

}
