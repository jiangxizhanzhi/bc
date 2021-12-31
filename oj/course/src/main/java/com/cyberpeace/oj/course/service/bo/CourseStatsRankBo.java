package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@ApiModel("课程学习排名")
public class CourseStatsRankBo implements Serializable {
    @ApiModelProperty("课程学习排名")
    private JSONObject peopleOrderingData;

    @ApiModelProperty("课程方向学习排名")
    private JSONObject directionOrderingData;

}
