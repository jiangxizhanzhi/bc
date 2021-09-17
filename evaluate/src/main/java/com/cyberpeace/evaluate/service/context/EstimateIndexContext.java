package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateIndex;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimateIndexContext extends EstimateIndex implements Serializable {

    /** 关联的变量项id*/
    @ApiModelProperty("关联的变量项id")
    private List<String> aggregation = Lists.newArrayList();

}
