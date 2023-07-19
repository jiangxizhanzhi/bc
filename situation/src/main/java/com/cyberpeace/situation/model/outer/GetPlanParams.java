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
public class GetPlanParams implements Serializable {

    @JsonProperty("taskType")
    private String taskType;

    @JsonProperty("taskSubtype")
    private String taskSubtype;

    @JsonProperty("commandData")
    private Object commandData;
}
