package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SituationPublishInstanceRes extends SituationPublishInstance implements Serializable {

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

}
