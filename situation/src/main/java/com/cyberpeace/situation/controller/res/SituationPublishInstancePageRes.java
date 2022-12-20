package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 13/12/2022 下午8:55
 */
@Data
public class SituationPublishInstancePageRes implements Serializable {

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private String category;

    @ApiModelProperty(value = "大屏状态")
    private String status;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("科目id")
    private String subjectId;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("大屏属性ID")
    private String attributeId;

}
