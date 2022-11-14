package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SituationPublishInstanceAddReq {

    @NotBlank(groups = {Create.class, Update.class },
            message = SituationErrorCode.NAME_EMPTY_ERROR)
    @ApiModelProperty(value = "可视化大屏名称", required = true)
    private String name;

    @ApiModelProperty(value = "大屏描述")
    private String description;

    @ApiModelProperty(value = "想定方案id")
    @NotBlank(groups = { Create.class, Update.class },
            message = SituationErrorCode.PLAN_ID_EMPTY_ERROR)
    private String planId;

    @ApiModelProperty(value = "任务id")
    @NotBlank(groups = { Create.class, Update.class },
            message = SituationErrorCode.TASK_EMPTY_ERROR)
    private String taskId;

    @ApiModelProperty(value = "科目id")
    @NotBlank(groups = { Create.class, Update.class },
            message = SituationErrorCode.SUBJECT_EMPTY_ERROR)
    private String subjectId;

    @ApiModelProperty(value = "模板id")
    @NotBlank(groups = { Create.class, Update.class },
            message = SituationErrorCode.TEMPLATE_EMPTY_ERROR)
    private String templateId;

    @ApiModelProperty(value = "任务类型", required = true)
    private String category;

    @NotBlank(groups = { Create.class, Update.class },
            message = SituationErrorCode.THUMBNAIL_EMPTY_ERROR)
    @ApiModelProperty(value = "缩略图", required = true)
    private String thumbnail;
}
