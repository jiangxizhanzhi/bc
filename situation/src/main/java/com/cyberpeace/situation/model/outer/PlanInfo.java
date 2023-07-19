package com.cyberpeace.situation.model.outer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanInfo implements Serializable {

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("subTaskId")
    private String subTaskId;

    @JsonProperty("subjectId")
    private String subjectId;

    @JsonProperty("planId")
    private String planId;

    @JsonProperty("subPlanId")
    private String subPlanId;

    @JsonProperty("schemeId")
    private String schemeId;

    @JsonProperty("commandData")
    private Object commandData;
}
