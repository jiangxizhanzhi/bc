package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimateAlgorithmContext implements Serializable {

    @ApiModelProperty("算法编号")
    private String estimateAlgorithmId;

    @ApiModelProperty("算法名称")
    private String algorithmName;

    @ApiModelProperty("算法类型")
    private String algorithmType;

    @ApiModelProperty("语言类型")
    private String languageType;

    @ApiModelProperty("算法脚本内容")
    private String algorithmContent;

    @ApiModelProperty("算法描述")
    private String algorithmDesc;

    @ApiModelProperty("算法参数数组")
    List<AlgorithmParam> params;

}
