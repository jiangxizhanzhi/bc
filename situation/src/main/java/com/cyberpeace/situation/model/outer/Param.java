package com.cyberpeace.situation.model.outer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Param implements Serializable {

    @JsonProperty("id")
    @ApiModelProperty("参数id")
    private String id;

    @JsonProperty("name")
    @ApiModelProperty("参数名称")
    private String name;

    @JsonProperty("dataType")
    @ApiModelProperty("参数类型")
    private String dataType;

    @JsonProperty("defaultValue")
    @ApiModelProperty("参数值")
    private Object defaultValue;

    @JsonProperty("dataDescripe")
    @ApiModelProperty("参数描述")
    private String dataDescripe;

}
