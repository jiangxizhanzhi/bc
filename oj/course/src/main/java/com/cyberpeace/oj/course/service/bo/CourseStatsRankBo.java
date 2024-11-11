package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程统计 排名 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("课程学习排名")
public class CourseStatsRankBo implements Serializable {
    /**
     * 订购数据人员
     */
    @ApiModelProperty("课程学习排名")
    private JSONObject peopleOrderingData;

    /**
     * 方向订购数据
     */
    @ApiModelProperty("课程方向学习排名")
    private JSONObject directionOrderingData;

}
