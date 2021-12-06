package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("单兵训练任务信息")
public class SingleMissionBo implements Serializable {
    @ApiModelProperty("任务标题")
    private String title;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("任务分类")
    private String category;

    @ApiModelProperty("题型")
    private Integer type;

    @ApiModelProperty("选项")
    private List<JSONObject> option;

    @ApiModelProperty("任务记录")
    private JSONObject trainingRecord;
}
